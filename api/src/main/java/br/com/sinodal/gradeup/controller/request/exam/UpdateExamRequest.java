package br.com.sinodal.gradeup.controller.request.exam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateExamRequest {

    @NotNull
    private Long classId;

    @NotNull
    private Long subjectId;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate examDate;
}