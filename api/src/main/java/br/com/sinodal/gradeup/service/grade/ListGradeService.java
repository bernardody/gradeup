package br.com.sinodal.gradeup.service.grade;

import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.mapper.grade.ListGradeMapper;
import br.com.sinodal.gradeup.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListGradeService {

    private final GradeRepository gradeRepository;

    public Page<GradeResponse> list(Pageable pageable) {
        Page<Grade> grades = gradeRepository.findAll(pageable);
        return grades.map(ListGradeMapper::toResponse);
    }
}