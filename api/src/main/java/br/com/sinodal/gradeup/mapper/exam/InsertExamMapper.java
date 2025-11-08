package br.com.sinodal.gradeup.mapper.exam;

import br.com.sinodal.gradeup.controller.request.exam.InsertExamRequest;
import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class InsertExamMapper {

    public static Exam toEntity(InsertExamRequest request, Class classEntity, Subject subject, User teacher) {
        return Exam.builder()
                .classEntity(classEntity)
                .subject(subject)
                .teacher(teacher)
                .name(request.getName())
                .examDate(request.getExamDate())
                .build();
    }

    public static ExamResponse toResponse(Exam entity) {
        return ExamResponse.builder()
                .id(entity.getId())
                .classEntity(ListClassMapper.toResponse(entity.getClassEntity()))
                .subject(ListSubjectMapper.toResponse(entity.getSubject()))
                .teacher(ListUserMapper.toResponse(entity.getTeacher()))
                .name(entity.getName())
                .examDate(entity.getExamDate())
                .build();
    }
}