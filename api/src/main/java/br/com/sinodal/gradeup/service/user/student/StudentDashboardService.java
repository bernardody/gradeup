package br.com.sinodal.gradeup.service.user.student;

import br.com.sinodal.gradeup.controller.response.subject.SubjectGradesResponse;
import br.com.sinodal.gradeup.controller.response.user.student.StudentDashboardResponse;
import br.com.sinodal.gradeup.domain.*;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.user.StudentDashboardMapper;
import br.com.sinodal.gradeup.repository.*;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        Registration registration = findStudentRegistration(student.getId());
        Clazz classEntity = registration.getClazz();

        List<Exam> allExams = examRepository.findByClazzId(classEntity.getId());
        List<Grade> studentGrades = gradeRepository.findByStudentId(student.getId());
        List<FinalExam> finalExams = finalExamRepository.findByClazzId(classEntity.getId());
        List<FinalGrade> finalGrades = finalGradeRepository.findByStudentId(student.getId());

        Map<Long, Grade> gradesByExamId = createGradesMap(studentGrades);
        Map<Long, FinalGrade> finalGradesByExamId = createFinalGradesMap(finalGrades);
        Map<Subject, List<Exam>> examsBySubject = groupExamsBySubject(allExams);

        List<SubjectGradesResponse> subjectResponses = examsBySubject.entrySet().stream()
                .map(entry -> StudentDashboardMapper.toSubjectResponse(
                        entry.getKey(),
                        entry.getValue(),
                        gradesByExamId,
                        finalExams,
                        finalGradesByExamId
                ))
                .sorted(Comparator.comparing(SubjectGradesResponse::getSubjectName))
                .collect(Collectors.toList());

        return StudentDashboardMapper.toResponse(student, classEntity, subjectResponses);
    }

    private Registration findStudentRegistration(Long studentId) {
        return registrationRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aluno não está matriculado em nenhuma turma"
                ));
    }

    private Map<Long, Grade> createGradesMap(List<Grade> grades) {
        return grades.stream()
                .collect(Collectors.toMap(g -> g.getExam().getId(), g -> g));
    }

    private Map<Long, FinalGrade> createFinalGradesMap(List<FinalGrade> finalGrades) {
        return finalGrades.stream()
                .collect(Collectors.toMap(fg -> fg.getFinalExam().getId(), fg -> fg));
    }

    private Map<Subject, List<Exam>> groupExamsBySubject(List<Exam> exams) {
        return exams.stream()
                .collect(Collectors.groupingBy(Exam::getSubject));
    }
}