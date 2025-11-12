package br.com.sinodal.gradeup.controller.request.finalexam;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateFinalExamRequest {

    @NotNull
    private Long classId;

    @NotNull
    private Long subjectId;

    @NotNull
    private LocalDate examDate;

    @NotNull
    private BigDecimal maxScore;

    @NotNull
    private BigDecimal minScore;
}