package br.com.sinodal.gradeup.mapper.teachersubjectclass;

import br.com.sinodal.gradeup.controller.response.teachersubjectclass.TeacherSubjectClassResponse;
import br.com.sinodal.gradeup.domain.TeacherSubjectClass;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class UpdateTeacherSubjectClassMapper {

    public static TeacherSubjectClassResponse toResponse(TeacherSubjectClass entity) {
        return TeacherSubjectClassResponse.builder()
                .id(entity.getId())
                .teacher(ListUserMapper.toResponse(entity.getTeacher()))
                .subject(ListSubjectMapper.toResponse(entity.getSubject()))
                .classEntity(ListClassMapper.toResponse(entity.getClazz()))
                .build();
    }
}