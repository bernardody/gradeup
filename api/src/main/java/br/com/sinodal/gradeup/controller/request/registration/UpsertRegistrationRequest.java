package br.com.sinodal.gradeup.controller.request.registration;

import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertRegistrationRequest {

    @NotNull
    private Long class_id;

    @NotNull
    private Long student_id;
}
