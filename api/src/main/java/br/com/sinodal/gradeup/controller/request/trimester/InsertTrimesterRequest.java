package br.com.sinodal.gradeup.controller.request.trimester;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InsertTrimesterRequest {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal maxPoints;

    @NotNull
    private BigDecimal minPoints;
}