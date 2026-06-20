package br.com.sinodal.gradeup.controller.request.grade;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InsertGradeRequest {

    @NotNull
    private Long examId;

    @NotNull
    private Long studentId;

    @NotNull
    private BigDecimal grade;
}