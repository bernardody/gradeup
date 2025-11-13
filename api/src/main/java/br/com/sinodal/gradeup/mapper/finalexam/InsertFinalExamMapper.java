package br.com.sinodal.gradeup.mapper.finalexam;

import br.com.sinodal.gradeup.controller.request.finalexam.InsertFinalExamRequest;
import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.FinalExam;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class InsertFinalExamMapper {

    public static FinalExam toEntity(InsertFinalExamRequest request, Class classEntity, Subject subject, User teacher) {
        return FinalExam.builder()
                .classEntity(classEntity)
                .subject(subject)
                .teacher(teacher)
                .examDate(request.getExamDate())
                .maxScore(request.getMaxScore())
                .minScore(request.getMinScore())
                .build();
    }

    public static FinalExamResponse toResponse(FinalExam entity) {
        return FinalExamResponse.builder()
                .id(entity.getId())
                .classEntity(ListClassMapper.toResponse(entity.getClassEntity()))
                .subject(ListSubjectMapper.toResponse(entity.getSubject()))
                .teacher(ListUserMapper.toResponse(entity.getTeacher()))
                .examDate(entity.getExamDate())
                .maxScore(entity.getMaxScore())
                .minScore(entity.getMinScore())
                .build();
    }
}