package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.subject.UpsertSubjectRequest;
import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.service.subject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final ListSubjectService listSubjectService;
    private final GetSubjectService getSubjectService;
    private final InsertSubjectService createSubjectService;
    private final UpdateSubjectService updateSubjectService;
    private final DeleteSubjectService deleteSubjectService;
    private final ListSubjectsByStudentService listSubjectsByStudentService;

    @GetMapping
    public Page<SubjectResponse> listSubjects(Pageable pageable) {
        return listSubjectService.list(pageable);
    }

    @GetMapping("/my-subjects")
    public List<SubjectResponse> listMySubjects(@RequestParam Long classId) {
        return listSubjectsByStudentService.list(classId);
    }

    @GetMapping("/{id}")
    public SubjectResponse getSubject(@PathVariable Long id) {
        return getSubjectService.byId(id);
    }

    @PostMapping
    public SubjectResponse insertSubject(@RequestBody UpsertSubjectRequest request) {
        return createSubjectService.insert(request);
    }

    @PutMapping("/{id}")
    public SubjectResponse updateSubject(@PathVariable Long id, @RequestBody UpsertSubjectRequest request) {
        return updateSubjectService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(@PathVariable Long id) {
        deleteSubjectService.delete(id);
    }
}