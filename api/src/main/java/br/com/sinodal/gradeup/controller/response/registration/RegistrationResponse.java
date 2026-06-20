package br.com.sinodal.gradeup.controller.response.registration;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationResponse {

    private Long id;
    private ClassResponse classEntity;
    private UserResponse student;
}