package br.com.sinodal.gradeup.mapper.classes;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.domain.Clazz;

public class UpdateClassMapper {
    public static ClassResponse toResponse(Clazz entity) {
        return ClassResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .year(entity.getYear())
                .build();
    }
}
