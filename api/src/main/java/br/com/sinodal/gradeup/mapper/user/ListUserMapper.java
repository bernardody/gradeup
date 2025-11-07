package br.com.sinodal.gradeup.mapper.user;

import br.com.sinodal.gradeup.controller.response.user.ListUserResponse;
import br.com.sinodal.gradeup.domain.User;

public class ListUserMapper {
    public static ListUserResponse toResponse(User entity) {
        return ListUserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .type(entity.getType())
                .build();
    }
}
