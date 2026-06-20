package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.finalexam.InsertFinalExamRequest;
import br.com.sinodal.gradeup.controller.request.finalexam.UpdateFinalExamRequest;
import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamResponse;
import br.com.sinodal.gradeup.service.finalexam.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/final-exams")
public class FinalExamController {

    private final ListFinalExamService listFinalExamService;
    private final GetFinalExamService getFinalExamService;
    private final InsertFinalExamService insertFinalExamService;
    private final UpdateFinalExamService updateFinalExamService;
    private final DeleteFinalExamService deleteFinalExamService;

    @GetMapping
    public Page<FinalExamResponse> listFinalExams(Pageable pageable) {
        return listFinalExamService.list(pageable);
    }

    @GetMapping("/{id}")
    public FinalExamResponse getFinalExam(@PathVariable Long id) {
        return getFinalExamService.byId(id);
    }

    @PostMapping
    public FinalExamResponse insertFinalExam(@RequestBody InsertFinalExamRequest request) {
        return insertFinalExamService.insert(request);
    }

    @PutMapping("/{id}")
    public FinalExamResponse updateFinalExam(@PathVariable Long id, @RequestBody UpdateFinalExamRequest request) {
        return updateFinalExamService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFinalExam(@PathVariable Long id) {
        deleteFinalExamService.delete(id);
    }
}