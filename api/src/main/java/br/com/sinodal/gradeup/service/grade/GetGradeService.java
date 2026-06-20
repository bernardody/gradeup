package br.com.sinodal.gradeup.service.grade;

import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.mapper.grade.ListGradeMapper;
import br.com.sinodal.gradeup.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetGradeService {

    private final GradeRepository gradeRepository;

    public GradeResponse byId(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada"));

        return ListGradeMapper.toResponse(grade);
    }
}