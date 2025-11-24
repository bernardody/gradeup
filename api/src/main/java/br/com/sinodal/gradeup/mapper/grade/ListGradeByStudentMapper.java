package br.com.sinodal.gradeup.mapper.grade;

import br.com.sinodal.gradeup.controller.response.grade.GradeByStudentResponse;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.mapper.exam.ListExamGradeMapper;

public class ListGradeByStudentMapper {

    public static GradeByStudentResponse toResponse(Grade entity) {
        return GradeByStudentResponse.builder()
                .gradeId(entity.getId())
                .exam(ListExamGradeMapper.toResponse(entity.getExam()))
                .studentId(entity.getStudent().getId())
                .grade(entity.getGrade())
                .build();
    }
}
