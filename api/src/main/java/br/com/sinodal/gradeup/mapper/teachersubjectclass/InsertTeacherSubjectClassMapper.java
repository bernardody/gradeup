package br.com.sinodal.gradeup.mapper.teachersubjectclass;

import br.com.sinodal.gradeup.controller.request.teachersubjectclass.InsertTeacherSubjectClassRequest;
import br.com.sinodal.gradeup.controller.response.teachersubjectclass.TeacherSubjectClassResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.TeacherSubjectClass;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class InsertTeacherSubjectClassMapper {

    public static TeacherSubjectClass toEntity(InsertTeacherSubjectClassRequest request, User teacher, Subject subject, Class classEntity) {
        return TeacherSubjectClass.builder()
                .teacher(teacher)
                .subject(subject)
                .classEntity(classEntity)
                .build();
    }

    public static TeacherSubjectClassResponse toResponse(TeacherSubjectClass entity) {
        return TeacherSubjectClassResponse.builder()
                .id(entity.getId())
                .teacher(ListUserMapper.toResponse(entity.getTeacher()))
                .subject(ListSubjectMapper.toResponse(entity.getSubject()))
                .classEntity(ListClassMapper.toResponse(entity.getClassEntity()))
                .build();
    }
}