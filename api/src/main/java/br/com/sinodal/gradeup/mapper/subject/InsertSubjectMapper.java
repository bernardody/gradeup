package br.com.sinodal.gradeup.mapper.subject;

import br.com.sinodal.gradeup.controller.request.subject.UpsertSubjectRequest;
import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.domain.Subject;

public class InsertSubjectMapper {
    public static Subject toEntity(UpsertSubjectRequest request) {
        return Subject.builder()
                .name(request.getName())
                .build();
    }

    public static SubjectResponse toResponse(Subject entity) {
        return SubjectResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}