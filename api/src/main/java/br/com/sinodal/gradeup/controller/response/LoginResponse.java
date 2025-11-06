package br.com.sinodal.gradeup.controller.response;

public record LoginResponse(String accessToken, Long expiresIn) {
}
