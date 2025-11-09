package br.com.sinodal.gradeup.mapper.registration;

import br.com.sinodal.gradeup.controller.response.registration.RegistrationResponse;
import br.com.sinodal.gradeup.domain.Registration;

public class ListRegistrationMapper {

    public static RegistrationResponse toResponse(Registration entity) {
        return RegistrationResponse.builder()
                .id(entity.getId())
                .classEntity(entity.getClassEntity())
                .student(entity.getStudent())
                .build();
    }
}
