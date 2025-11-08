package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.exam.InsertExamRequest;
import br.com.sinodal.gradeup.controller.request.exam.UpdateExamRequest;
import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.service.exam.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ListExamService listExamService;
    private final GetExamService getExamService;
    private final InsertExamService insertExamService;
    private final UpdateExamService updateExamService;
    private final DeleteExamService deleteExamService;

    @GetMapping
    public Page<ExamResponse> listExams(Pageable pageable) {
        return listExamService.list(pageable);
    }

    @GetMapping("/{id}")
    public ExamResponse getExam(@PathVariable Long id) {
        return getExamService.byId(id);
    }

    @PostMapping
    public ExamResponse insertExam(@RequestBody InsertExamRequest request) {
        return insertExamService.insert(request);
    }

    @PutMapping("/{id}")
    public ExamResponse updateExam(@PathVariable Long id, @RequestBody UpdateExamRequest request) {
        return updateExamService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExam(@PathVariable Long id) {
        deleteExamService.delete(id);
    }
}