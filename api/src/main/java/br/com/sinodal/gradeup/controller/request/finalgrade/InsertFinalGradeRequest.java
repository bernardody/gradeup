package br.com.sinodal.gradeup.controller.request.finalgrade;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InsertFinalGradeRequest {

    @NotNull
    private Long finalExamId;

    @NotNull
    private Long studentId;

    @NotNull
    private BigDecimal grade;
}