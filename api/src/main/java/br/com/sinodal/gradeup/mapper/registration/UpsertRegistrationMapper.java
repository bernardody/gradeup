package br.com.sinodal.gradeup.mapper.registration;

import br.com.sinodal.gradeup.controller.request.registration.UpsertRegistrationRequest;
import br.com.sinodal.gradeup.controller.response.registration.RegistrationResponse;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.domain.Registration;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class UpsertRegistrationMapper {

    public static Registration toEntity(UpsertRegistrationRequest request, User student, Clazz clazz) {
        return Registration.builder()
                .clazz(clazz)
                .student(student)
                .build();
    }

    public static RegistrationResponse toResponse(Registration entity) {
        return RegistrationResponse.builder()
                .id(entity.getId())
                .classEntity(ListClassMapper.toResponse(entity.getClazz()))
                .student(ListUserMapper.toResponse(entity.getStudent()))
                .build();
    }
}