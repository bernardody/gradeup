package br.com.sinodal.gradeup.mapper.user;

import br.com.sinodal.gradeup.controller.request.user.InsertUserRequest;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import br.com.sinodal.gradeup.domain.User;

public class InsertUserMapper {

    public static User toEntity(InsertUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .type(request.getType())
                .build();
    }

    public static UserResponse toResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .type(entity.getType())
                .build();
    }
}
