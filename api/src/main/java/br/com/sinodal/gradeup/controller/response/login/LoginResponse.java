package br.com.sinodal.gradeup.controller.response.login;

public record LoginResponse(String accessToken, Long expiresIn) {
}
