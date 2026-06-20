package br.com.sinodal.gradeup.mapper.warning;

import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.mapper.exam.ListExamMapper;

public class UpdateWarningMapper {

    public static WarningResponse toResponse(Warning warning) {
        return WarningResponse.builder()
                .id(warning.getId())
                .title(warning.getTitle())
                .content(warning.getContent())
                .examEntity(ListExamMapper.toResponse(warning.getExam()))
                .createdAt(warning.getCreatedAt())
                .build();
    }
}
