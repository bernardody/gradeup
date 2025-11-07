package br.com.sinodal.gradeup.mapper.aClass;

import br.com.sinodal.gradeup.controller.request.aClass.CreateClassRequest;
import br.com.sinodal.gradeup.controller.response.aClass.ListClassResponse;
import br.com.sinodal.gradeup.domain.Class;

public class UpdateClassMapper {
    public static ListClassResponse toResponse(Class entity) {
        return ListClassResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .year(entity.getYear())
                .build();
    }
}
