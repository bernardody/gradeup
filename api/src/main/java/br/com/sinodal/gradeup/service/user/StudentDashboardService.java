package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.controller.response.classes.ClassInfoResponse;
import br.com.sinodal.gradeup.controller.response.exam.ExamGradeResponse;
import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamGradeResponse;
import br.com.sinodal.gradeup.controller.response.subject.SubjectGradesResponse;
import br.com.sinodal.gradeup.controller.response.trimester.TrimesterGradesResponse;
import br.com.sinodal.gradeup.controller.response.user.*;
import br.com.sinodal.gradeup.domain.*;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentDashboardService {

    private final AuthenticatedUserService authenticatedUserService;
    private final RegistrationRepository registrationRepository;
    private final ExamRepository examRepository;
    private final GradeRepository gradeRepository;
    private final FinalExamRepository finalExamRepository;
    private final FinalGradeRepository finalGradeRepository;

    public StudentDashboardResponse getDashboard() {
        User student = authenticatedUserService.get();

        if (!student.getType().equals(UserType.STUDENT)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }

        Registration registration = registrationRepository.findByStudentId(student.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não está matriculado em nenhuma turma"));

        Clazz clazz = registration.getClazz();

        List<Exam> allExams = examRepository.findByClazzId(clazz.getId());

        Map<Subject, List<Exam>> examsBySubject = allExams.stream()
                .collect(Collectors.groupingBy(Exam::getSubject));

        List<Grade> studentGrades = gradeRepository.findByStudentId(student.getId());
        Map<Long, Grade> gradesByExamId = studentGrades.stream()
                .collect(Collectors.toMap(g -> g.getExam().getId(), g -> g));

        List<FinalExam> finalExams = finalExamRepository.findByClazzId(clazz.getId());

        List<FinalGrade> finalGrades = finalGradeRepository.findByStudentId(student.getId());
        Map<Long, FinalGrade> finalGradesByExamId = finalGrades.stream()
                .collect(Collectors.toMap(fg -> fg.getFinalExam().getId(), fg -> fg));

        List<SubjectGradesResponse> subjectResponses = examsBySubject.entrySet().stream()
                .map(entry -> buildSubjectResponse(
                        entry.getKey(),
                        entry.getValue(),
                        gradesByExamId,
                        finalExams,
                        finalGradesByExamId
                ))
                .sorted(Comparator.comparing(SubjectGradesResponse::getSubjectName))
                .collect(Collectors.toList());

        return StudentDashboardResponse.builder()
                .studentId(student.getId())
                .studentName(student.getName())
                .studentEmail(student.getEmail())
                .classInfo(ClassInfoResponse.builder()
                        .id(clazz.getId())
                        .name(clazz.getName())
                        .year(clazz.getYear())
                        .build())
                .subjects(subjectResponses)
                .build();
    }

    private SubjectGradesResponse buildSubjectResponse(
            Subject subject,
            List<Exam> exams,
            Map<Long, Grade> gradesByExamId,
            List<FinalExam> allFinalExams,
            Map<Long, FinalGrade> finalGradesByExamId
    ) {
        Map<Trimester, List<Exam>> examsByTrimester = exams.stream()
                .collect(Collectors.groupingBy(Exam::getTrimester));

        List<TrimesterGradesResponse> trimesterResponses = examsByTrimester.entrySet().stream()
                .map(entry -> buildTrimesterResponse(entry.getKey(), entry.getValue(), gradesByExamId))
                .sorted(Comparator.comparing(TrimesterGradesResponse::getTrimesterId))
                .collect(Collectors.toList());

        FinalExam finalExam = allFinalExams.stream()
                .filter(fe -> fe.getSubject().getId().equals(subject.getId()))
                .findFirst()
                .orElse(null);

        FinalExamGradeResponse finalExamResponse = null;
        if (finalExam != null) {
            FinalGrade finalGrade = finalGradesByExamId.get(finalExam.getId());
            if (finalGrade != null) {
                finalExamResponse = FinalExamGradeResponse.builder()
                        .finalExamId(finalExam.getId())
                        .examDate(finalExam.getExamDate())
                        .maxScore(finalExam.getMaxScore())
                        .minScore(finalExam.getMinScore())
                        .studentGrade(finalGrade.getGrade())
                        .passed(finalGrade.getGrade().compareTo(finalExam.getMinScore()) >= 0)
                        .build();
            }
        }

        BigDecimal average = calculateSubjectAverage(trimesterResponses);

        return SubjectGradesResponse.builder()
                .subjectId(subject.getId())
                .subjectName(subject.getName())
                .trimesters(trimesterResponses)
                .finalExam(finalExamResponse)
                .average(average)
                .build();
    }

    private TrimesterGradesResponse buildTrimesterResponse(
            Trimester trimester,
            List<Exam> exams,
            Map<Long, Grade> gradesByExamId
    ) {
        List<ExamGradeResponse> examResponses = exams.stream()
                .map(exam -> {
                    Grade grade = gradesByExamId.get(exam.getId());
                    BigDecimal studentGrade = grade != null ? grade.getGrade() : null;
                    BigDecimal percentage = null;

                    if (studentGrade != null && exam.getMaxScore().compareTo(BigDecimal.ZERO) > 0) {
                        percentage = studentGrade
                                .divide(exam.getMaxScore(), 4, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal("100"))
                                .setScale(2, RoundingMode.HALF_UP);
                    }

                    return ExamGradeResponse.builder()
                            .examId(exam.getId())
                            .examName(exam.getName())
                            .examDate(exam.getExamDate())
                            .maxScore(exam.getMaxScore())
                            .studentGrade(studentGrade)
                            .percentage(percentage)
                            .build();
                })
                .sorted(Comparator.comparing(ExamGradeResponse::getExamDate))
                .collect(Collectors.toList());

        BigDecimal totalScore = examResponses.stream()
                .map(ExamGradeResponse::getStudentGrade)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Boolean passed = totalScore.compareTo(trimester.getMinPoints()) >= 0;

        return TrimesterGradesResponse.builder()
                .trimesterId(trimester.getId())
                .trimesterName(trimester.getName())
                .maxPoints(trimester.getMaxPoints())
                .minPoints(trimester.getMinPoints())
                .exams(examResponses)
                .totalScore(totalScore)
                .passed(passed)
                .build();
    }

    private BigDecimal calculateSubjectAverage(List<TrimesterGradesResponse> trimesters) {
        if (trimesters.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = trimesters.stream()
                .map(TrimesterGradesResponse::getTotalScore)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(new BigDecimal(trimesters.size()), 2, RoundingMode.HALF_UP);
    }
}