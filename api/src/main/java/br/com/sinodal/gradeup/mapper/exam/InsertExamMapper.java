package br.com.sinodal.gradeup.mapper.exam;

import br.com.sinodal.gradeup.controller.request.exam.InsertExamRequest;
import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.Trimester;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.mapper.trimester.ListTrimesterMapper;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;

public class InsertExamMapper {

    public static Exam toEntity(InsertExamRequest request, Clazz clazz, Subject subject, User teacher, Trimester trimester) {
        return Exam.builder()
                .clazz(clazz)
                .subject(subject)
                .teacher(teacher)
                .trimester(trimester)
                .name(request.getName())
                .maxScore(request.getMaxScore())
                .examDate(request.getExamDate())
                .build();
    }

    public static ExamResponse toResponse(Exam entity) {
        return ExamResponse.builder()
                .id(entity.getId())
                .classEntity(ListClassMapper.toResponse(entity.getClazz()))
                .subject(ListSubjectMapper.toResponse(entity.getSubject()))
                .teacher(ListUserMapper.toResponse(entity.getTeacher()))
                .trimester(ListTrimesterMapper.toResponse(entity.getTrimester()))
                .name(entity.getName())
                .maxScore(entity.getMaxScore())
                .examDate(entity.getExamDate())
                .build();
    }
}