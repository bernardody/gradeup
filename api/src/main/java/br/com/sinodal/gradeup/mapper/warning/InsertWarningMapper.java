package br.com.sinodal.gradeup.mapper.warning;

import br.com.sinodal.gradeup.controller.request.warning.InsertWarningRequest;
import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.mapper.exam.ListExamMapper;

public class InsertWarningMapper {

    public static Warning toEntity(InsertWarningRequest request, Exam exam) {
        return Warning.builder()
                .exam(exam)
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public static WarningResponse toResponse(Warning entity) {
        return WarningResponse.builder()
                .id(entity.getId())
                .examEntity(ListExamMapper.toResponse(entity.getExam()))
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
