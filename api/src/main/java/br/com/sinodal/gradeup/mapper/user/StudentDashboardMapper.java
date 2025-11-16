package br.com.sinodal.gradeup.mapper.user;

import br.com.sinodal.gradeup.controller.response.classes.ClassInfoResponse;
import br.com.sinodal.gradeup.controller.response.exam.ExamGradeResponse;
import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamGradeResponse;
import br.com.sinodal.gradeup.controller.response.subject.SubjectGradesResponse;
import br.com.sinodal.gradeup.controller.response.trimester.TrimesterGradesResponse;
import br.com.sinodal.gradeup.controller.response.user.student.StudentDashboardResponse;
import br.com.sinodal.gradeup.domain.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class StudentDashboardMapper {

    public static StudentDashboardResponse toResponse(
            User student,
            Clazz clazz,
            List<SubjectGradesResponse> subjects
    ) {
        return StudentDashboardResponse.builder()
                .studentId(student.getId())
                .studentName(student.getName())
                .studentEmail(student.getEmail())
                .classInfo(ClassInfoResponse.builder()
                        .id(clazz.getId())
                        .name(clazz.getName())
                        .year(clazz.getYear())
                        .build())
                .subjects(subjects)
                .build();
    }

    public static SubjectGradesResponse toSubjectResponse(
            Subject subject,
            List<Exam> exams,
            Map<Long, Grade> gradesByExamId,
            List<FinalExam> allFinalExams,
            Map<Long, FinalGrade> finalGradesByExamId
    ) {
        Map<Trimester, List<Exam>> examsByTrimester = exams.stream()
                .collect(Collectors.groupingBy(Exam::getTrimester));

        List<TrimesterGradesResponse> trimesterResponses = examsByTrimester.entrySet().stream()
                .map(entry -> toTrimesterResponse(entry.getKey(), entry.getValue(), gradesByExamId))
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
                finalExamResponse = toFinalExamResponse(finalExam, finalGrade);
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

    public static TrimesterGradesResponse toTrimesterResponse(
            Trimester trimester,
            List<Exam> exams,
            Map<Long, Grade> gradesByExamId
    ) {
        List<ExamGradeResponse> examResponses = exams.stream()
                .map(exam -> toExamGradeResponse(exam, gradesByExamId.get(exam.getId())))
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

    public static ExamGradeResponse toExamGradeResponse(Exam exam, Grade grade) {
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
    }

    public static FinalExamGradeResponse toFinalExamResponse(FinalExam finalExam, FinalGrade finalGrade) {
        return FinalExamGradeResponse.builder()
                .finalExamId(finalExam.getId())
                .examDate(finalExam.getExamDate())
                .maxScore(finalExam.getMaxScore())
                .minScore(finalExam.getMinScore())
                .studentGrade(finalGrade.getGrade())
                .passed(finalGrade.getGrade().compareTo(finalExam.getMinScore()) >= 0)
                .build();
    }

    private static BigDecimal calculateSubjectAverage(List<TrimesterGradesResponse> trimesters) {
        if (trimesters.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = trimesters.stream()
                .map(TrimesterGradesResponse::getTotalScore)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(new BigDecimal(trimesters.size()), 2, RoundingMode.HALF_UP);
    }
}