package br.com.sinodal.gradeup.controller.request.aClass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClassRequest {

    @NotBlank
    private String name;

    @NotNull
    private int year;
}
