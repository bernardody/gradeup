package br.com.sinodal.gradeup.controller.request.warning;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWarningRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
