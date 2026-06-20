package br.com.sinodal.gradeup.controller.request.registration;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertRegistrationRequest {

    @NotNull
    private Long classId;

    @NotNull
    private Long studentId;
}