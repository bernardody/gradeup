package br.com.sinodal.gradeup.mapper.exam;

import br.com.sinodal.gradeup.controller.response.exam.ExamGradeByStudentResponse;
import br.com.sinodal.gradeup.domain.Exam;
import jakarta.validation.constraints.NotNull;

public class ListExamGradeMapper {
    public static ExamGradeByStudentResponse toResponse(Exam entity) {
        return ExamGradeByStudentResponse.builder()
                .examId(entity.getId())
                .classId(entity.getClazz().getId())
                .subjectId(entity.getSubject().getId())
                .teacherId(entity.getTeacher().getId())
                .trimesterId(entity.getTrimester().getId())
                .examName(entity.getName())
                .maxScore(entity.getMaxScore())
                .examDate(entity.getExamDate())
                .build();
    }
}
