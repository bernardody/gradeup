package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.finalgrade.InsertFinalGradeRequest;
import br.com.sinodal.gradeup.controller.request.finalgrade.UpdateFinalGradeRequest;
import br.com.sinodal.gradeup.controller.response.finalgrade.FinalGradeResponse;
import br.com.sinodal.gradeup.service.finalgrade.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/final-grades")
public class FinalGradeController {

    private final ListFinalGradeService listFinalGradeService;
    private final GetFinalGradeService getFinalGradeService;
    private final InsertFinalGradeService insertFinalGradeService;
    private final UpdateFinalGradeService updateFinalGradeService;
    private final DeleteFinalGradeService deleteFinalGradeService;

    @GetMapping
    public Page<FinalGradeResponse> listFinalGrades(Pageable pageable) {
        return listFinalGradeService.list(pageable);
    }

    @GetMapping("/{id}")
    public FinalGradeResponse getFinalGrade(@PathVariable Long id) {
        return getFinalGradeService.byId(id);
    }

    @PostMapping
    public FinalGradeResponse insertFinalGrade(@RequestBody InsertFinalGradeRequest request) {
        return insertFinalGradeService.insert(request);
    }

    @PutMapping("/{id}")
    public FinalGradeResponse updateFinalGrade(@PathVariable Long id, @RequestBody UpdateFinalGradeRequest request) {
        return updateFinalGradeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFinalGrade(@PathVariable Long id) {
        deleteFinalGradeService.delete(id);
    }
}