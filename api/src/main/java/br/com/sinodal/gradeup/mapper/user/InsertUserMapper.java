package br.com.sinodal.gradeup.mapper.user;

import br.com.sinodal.gradeup.controller.request.user.CreateUserRequest;
import br.com.sinodal.gradeup.controller.response.user.ListUserResponse;
import br.com.sinodal.gradeup.domain.User;

public class InsertUserMapper {

    public static User toEntity(CreateUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .type(request.getType())
                .build();
    }

    public static ListUserResponse toResponse(User entity) {
        return ListUserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .type(entity.getType())
                .build();
    }
}
