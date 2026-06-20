package br.com.sinodal.gradeup.mapper.exam;

import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.mapper.trimester.ListTrimesterMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class ListExamMapper {

    public static ExamResponse toResponse(Exam entity) {
        return ExamResponse.builder()
                .id(entity.getId())
                .classEntity(ListClassMapper.toResponse(entity.getClazz()))
                .subject(ListSubjectMapper.toResponse(entity.getSubject()))
                .teacher(ListUserMapper.toResponse(entity.getTeacher()))
                .trimester(ListTrimesterMapper.toResponse(entity.getTrimester()))
                .name(entity.getName())
                .maxScore(entity.getMaxScore())
                .examDate(entity.getExamDate())
                .build();
    }
}