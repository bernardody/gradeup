package br.com.sinodal.gradeup.mapper.subject;

import br.com.sinodal.gradeup.controller.request.subject.CreateSubjectRequest;
import br.com.sinodal.gradeup.controller.response.subject.ListSubjectResponse;
import br.com.sinodal.gradeup.domain.Subject;

public class CreateSubjectMapper {
    public static Subject toEntity(CreateSubjectRequest request) {
        return Subject.builder()
                .name(request.getName())
                .build();
    }

    public static ListSubjectResponse toResponse(Subject entity) {
        return ListSubjectResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}