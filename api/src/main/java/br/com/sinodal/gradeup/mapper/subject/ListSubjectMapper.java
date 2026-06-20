package br.com.sinodal.gradeup.mapper.subject;

import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.domain.Subject;

public class ListSubjectMapper {
    public static SubjectResponse toResponse(Subject entity) {
        return SubjectResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}