package br.com.sinodal.gradeup.service.finalexam;

import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamResponse;
import br.com.sinodal.gradeup.domain.FinalExam;
import br.com.sinodal.gradeup.mapper.finalexam.ListFinalExamMapper;
import br.com.sinodal.gradeup.repository.FinalExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetFinalExamService {

    private final FinalExamRepository finalExamRepository;

    public FinalExamResponse byId(Long id) {
        FinalExam finalExam = finalExamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova final não encontrada"));

        return ListFinalExamMapper.toResponse(finalExam);
    }
}