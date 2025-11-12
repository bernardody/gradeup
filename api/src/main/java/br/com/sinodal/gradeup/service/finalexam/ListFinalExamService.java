package br.com.sinodal.gradeup.service.finalexam;

import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamResponse;
import br.com.sinodal.gradeup.domain.FinalExam;
import br.com.sinodal.gradeup.mapper.finalexam.ListFinalExamMapper;
import br.com.sinodal.gradeup.repository.FinalExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListFinalExamService {

    private final FinalExamRepository finalExamRepository;

    public Page<FinalExamResponse> list(Pageable pageable) {
        Page<FinalExam> finalExams = finalExamRepository.findAll(pageable);
        return finalExams.map(ListFinalExamMapper::toResponse);
    }
}