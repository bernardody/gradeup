package br.com.sinodal.gradeup.mapper.aClass;


import br.com.sinodal.gradeup.controller.request.aClass.CreateClassRequest;
import br.com.sinodal.gradeup.controller.response.aClass.ListClassResponse;
import br.com.sinodal.gradeup.domain.Class;

public class CreateClassMapper {
    public static Class toEntity(CreateClassRequest request) {
        return Class.builder()
                .name(request.getName())
                .year(request.getYear())
                .build();
    }

    public static ListClassResponse toResponse(Class entity) {
        return ListClassResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .year(entity.getYear())
                .build();
    }
}
