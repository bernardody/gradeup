package br.com.sinodal.gradeup.mapper.grade;

import br.com.sinodal.gradeup.controller.request.grade.InsertGradeRequest;
import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.exam.ListExamMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class InsertGradeMapper {

    public static Grade toEntity(InsertGradeRequest request, Exam exam, User student) {
        return Grade.builder()
                .exam(exam)
                .student(student)
                .grade(request.getGrade())
                .build();
    }

    public static GradeResponse toResponse(Grade entity) {
        return GradeResponse.builder()
                .id(entity.getId())
                .exam(ListExamMapper.toResponse(entity.getExam()))
                .student(ListUserMapper.toResponse(entity.getStudent()))
                .grade(entity.getGrade())
                .build();
    }
}