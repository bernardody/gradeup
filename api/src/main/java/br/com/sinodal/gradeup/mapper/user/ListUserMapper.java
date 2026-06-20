package br.com.sinodal.gradeup.mapper.user;

import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import br.com.sinodal.gradeup.domain.User;

public class ListUserMapper {
    public static UserResponse toResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .type(entity.getType())
                .build();
    }
}
