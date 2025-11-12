package br.com.sinodal.gradeup.service.finalgrade;

import br.com.sinodal.gradeup.controller.response.finalgrade.FinalGradeResponse;
import br.com.sinodal.gradeup.domain.FinalGrade;
import br.com.sinodal.gradeup.mapper.finalgrade.ListFinalGradeMapper;
import br.com.sinodal.gradeup.repository.FinalGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetFinalGradeService {

    private final FinalGradeRepository finalGradeRepository;

    public FinalGradeResponse byId(Long id) {
        FinalGrade finalGrade = finalGradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota final não encontrada"));

        return ListFinalGradeMapper.toResponse(finalGrade);
    }
}