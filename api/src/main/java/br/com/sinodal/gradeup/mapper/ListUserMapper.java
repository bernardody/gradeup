package br.com.sinodal.gradeup.mapper;

import br.com.sinodal.gradeup.controller.response.ListUserResponse;
import br.com.sinodal.gradeup.domain.Users;

public class ListUserMapper {
    public static ListUserResponse toResponse(Users entity) {
        return ListUserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .type(entity.getType())
                .build();
    }
}
