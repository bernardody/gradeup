package br.com.sinodal.gradeup.service.exam;

import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.mapper.exam.ListExamMapper;
import br.com.sinodal.gradeup.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListExamService {

    private final ExamRepository examRepository;

    public Page<ExamResponse> list(Pageable pageable) {
        Page<Exam> exams = examRepository.findAll(pageable);
        return exams.map(ListExamMapper::toResponse);
    }
}