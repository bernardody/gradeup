package br.com.sinodal.gradeup.mapper.subject;

import br.com.sinodal.gradeup.controller.response.subject.ListSubjectResponse;
import br.com.sinodal.gradeup.domain.Subject;

public class ListSubjectMapper {
    public static ListSubjectResponse toResponse(Subject entity) {
        return ListSubjectResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}