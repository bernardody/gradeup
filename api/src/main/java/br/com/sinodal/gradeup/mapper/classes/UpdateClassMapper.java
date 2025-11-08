package br.com.sinodal.gradeup.mapper.classes;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.domain.Class;

public class UpdateClassMapper {
    public static ClassResponse toResponse(Class entity) {
        return ClassResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .year(entity.getYear())
                .build();
    }
}
