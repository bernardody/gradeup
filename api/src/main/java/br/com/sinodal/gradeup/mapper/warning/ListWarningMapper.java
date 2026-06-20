package br.com.sinodal.gradeup.mapper.warning;

import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.exam.ListExamMapper;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class ListWarningMapper {

    public static WarningResponse toResponse(Warning entity) {
        return WarningResponse.builder()
                .id(entity.getId())
                .examEntity(ListExamMapper.toResponse(entity.getExam()))
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
