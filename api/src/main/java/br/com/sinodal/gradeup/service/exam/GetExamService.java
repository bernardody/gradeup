package br.com.sinodal.gradeup.service.exam;

import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.mapper.exam.ListExamMapper;
import br.com.sinodal.gradeup.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetExamService {

    private final ExamRepository examRepository;

    public ExamResponse byId(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));

        return ListExamMapper.toResponse(exam);
    }
}