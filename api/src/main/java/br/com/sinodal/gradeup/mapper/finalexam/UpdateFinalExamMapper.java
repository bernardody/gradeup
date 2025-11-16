package br.com.sinodal.gradeup.mapper.finalexam;

import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamResponse;
import br.com.sinodal.gradeup.domain.FinalExam;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class UpdateFinalExamMapper {

    public static FinalExamResponse toResponse(FinalExam entity) {
        return FinalExamResponse.builder()
                .id(entity.getId())
                .classEntity(ListClassMapper.toResponse(entity.getClazz()))
                .subject(ListSubjectMapper.toResponse(entity.getSubject()))
                .teacher(ListUserMapper.toResponse(entity.getTeacher()))
                .examDate(entity.getExamDate())
                .maxScore(entity.getMaxScore())
                .minScore(entity.getMinScore())
                .build();
    }
}