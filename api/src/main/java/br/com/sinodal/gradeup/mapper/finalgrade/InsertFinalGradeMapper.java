package br.com.sinodal.gradeup.mapper.finalgrade;

import br.com.sinodal.gradeup.controller.request.finalgrade.InsertFinalGradeRequest;
import br.com.sinodal.gradeup.controller.response.finalgrade.FinalGradeResponse;
import br.com.sinodal.gradeup.domain.FinalExam;
import br.com.sinodal.gradeup.domain.FinalGrade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.finalexam.ListFinalExamMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class InsertFinalGradeMapper {

    public static FinalGrade toEntity(InsertFinalGradeRequest request, FinalExam finalExam, User student) {
        return FinalGrade.builder()
                .finalExam(finalExam)
                .student(student)
                .grade(request.getGrade())
                .build();
    }

    public static FinalGradeResponse toResponse(FinalGrade entity) {
        return FinalGradeResponse.builder()
                .id(entity.getId())
                .finalExam(ListFinalExamMapper.toResponse(entity.getFinalExam()))
                .student(ListUserMapper.toResponse(entity.getStudent()))
                .grade(entity.getGrade())
                .build();
    }
}