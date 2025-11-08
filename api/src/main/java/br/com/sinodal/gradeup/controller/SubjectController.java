package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.subject.CreateSubjectRequest;
import br.com.sinodal.gradeup.controller.response.subject.ListSubjectResponse;
import br.com.sinodal.gradeup.service.subject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final ListSubjectService listSubjectService;
    private final GetSubjectService getSubjectService;
    private final CreateSubjectService createSubjectService;
    private final UpdateSubjectService updateSubjectService;
    private final DeleteSubjectService deleteSubjectService;

    @GetMapping
    public Page<ListSubjectResponse> listSubjects(Pageable pageable) {
        return listSubjectService.list(pageable);
    }

    @GetMapping("/{id}")
    public ListSubjectResponse getSubject(@PathVariable Long id) {
        return getSubjectService.byId(id);
    }

    @PostMapping
    public ListSubjectResponse createSubject(@RequestBody CreateSubjectRequest request) {
        return createSubjectService.create(request);
    }

    @PutMapping("/{id}")
    public ListSubjectResponse updateSubject(@PathVariable Long id, @RequestBody CreateSubjectRequest request) {
        return updateSubjectService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(@PathVariable Long id) {
        deleteSubjectService.delete(id);
    }
}