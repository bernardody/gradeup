package br.com.sinodal.gradeup.service.finalgrade;

import br.com.sinodal.gradeup.controller.response.finalgrade.FinalGradeResponse;
import br.com.sinodal.gradeup.domain.FinalGrade;
import br.com.sinodal.gradeup.mapper.finalgrade.ListFinalGradeMapper;
import br.com.sinodal.gradeup.repository.FinalGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListFinalGradeService {

    private final FinalGradeRepository finalGradeRepository;

    public Page<FinalGradeResponse> list(Pageable pageable) {
        Page<FinalGrade> finalGrades = finalGradeRepository.findAll(pageable);
        return finalGrades.map(ListFinalGradeMapper::toResponse);
    }
}