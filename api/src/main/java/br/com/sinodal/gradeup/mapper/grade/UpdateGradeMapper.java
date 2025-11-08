package br.com.sinodal.gradeup.mapper.grade;

import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.mapper.exam.ListExamMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class UpdateGradeMapper {

    public static GradeResponse toResponse(Grade entity) {
        return GradeResponse.builder()
                .id(entity.getId())
                .exam(ListExamMapper.toResponse(entity.getExam()))
                .student(ListUserMapper.toResponse(entity.getStudent()))
                .grade(entity.getGrade())
                .build();
    }
}