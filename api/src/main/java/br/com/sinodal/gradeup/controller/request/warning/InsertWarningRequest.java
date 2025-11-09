package br.com.sinodal.gradeup.controller.request.warning;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertWarningRequest {

    @NotNull
    private Long exam_id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
