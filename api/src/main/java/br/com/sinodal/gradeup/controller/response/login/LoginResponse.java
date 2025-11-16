package br.com.sinodal.gradeup.controller.response.login;

import br.com.sinodal.gradeup.enums.UserType;

public record LoginResponse(String accessToken, Long expiresIn, UserType type) {
}
