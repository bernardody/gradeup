package br.com.sinodal.gradeup.mapper.classes;


import br.com.sinodal.gradeup.controller.request.classes.UpsertClassRequest;
import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.domain.Clazz;

public class InsertClassMapper {
    public static Clazz toEntity(UpsertClassRequest request) {
        return Clazz.builder()
                .name(request.getName())
                .year(request.getYear())
                .build();
    }

    public static ClassResponse toResponse(Clazz entity) {
        return ClassResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .year(entity.getYear())
                .build();
    }
}
