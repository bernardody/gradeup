package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.grade.InsertGradeRequest;
import br.com.sinodal.gradeup.controller.request.grade.UpdateGradeRequest;
import br.com.sinodal.gradeup.controller.response.grade.GradeByStudentResponse;
import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.service.grade.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/grades")
public class GradeController {

    private final ListGradeService listGradeService;
    private final ListMyGradesBySubjectService listMyGradesBySubjectService;
    private final GetGradeService getGradeService;
    private final InsertGradeService insertGradeService;
    private final UpdateGradeService updateGradeService;
    private final DeleteGradeService deleteGradeService;

    @GetMapping
    public Page<GradeResponse> listGrades(Pageable pageable) {
        return listGradeService.list(pageable);
    }

    @GetMapping("/my-grades")
    public List<GradeByStudentResponse> listMyGradesBySubject(@RequestParam Long subjectId) {
        return listMyGradesBySubjectService.list(subjectId);
    }

    @GetMapping("/{id}")
    public GradeResponse getGrade(@PathVariable Long id) {
        return getGradeService.byId(id);
    }

    @PostMapping
    public GradeResponse insertGrade(@RequestBody InsertGradeRequest request) {
        return insertGradeService.insert(request);
    }

    @PutMapping("/{id}")
    public GradeResponse updateGrade(@PathVariable Long id, @RequestBody UpdateGradeRequest request) {
        return updateGradeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrade(@PathVariable Long id) {
        deleteGradeService.delete(id);
    }
}