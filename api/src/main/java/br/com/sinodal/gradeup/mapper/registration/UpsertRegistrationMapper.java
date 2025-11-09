package br.com.sinodal.gradeup.mapper.registration;

import br.com.sinodal.gradeup.controller.request.registration.UpsertRegistrationRequest;
import br.com.sinodal.gradeup.controller.response.registration.RegistrationResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.Registration;
import br.com.sinodal.gradeup.domain.User;

public class UpsertRegistrationMapper {
    public static Registration toEntity(UpsertRegistrationRequest request, User student, Class classEntity) {
        return Registration.builder()
                .classEntity(classEntity)
                .student(student)
                .build();
    }

    public static RegistrationResponse toResponse(Registration entity) {
        return RegistrationResponse.builder()
                .id(entity.getId())
                .classEntity(entity.getClassEntity())
                .student(entity.getStudent())
                .build();
    }
}
