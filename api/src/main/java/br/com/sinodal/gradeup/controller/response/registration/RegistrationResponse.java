package br.com.sinodal.gradeup.controller.response.registration;

import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.User;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationResponse {

    private Long id;
    private Class classEntity;
    private User student;
}
