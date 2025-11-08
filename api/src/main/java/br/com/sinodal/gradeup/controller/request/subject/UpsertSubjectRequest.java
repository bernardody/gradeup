package br.com.sinodal.gradeup.controller.request.subject;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertSubjectRequest {

    @NotBlank
    private String name;
}