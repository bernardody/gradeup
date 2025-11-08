package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.classes.UpsertClassRequest;
import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.service.classes.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/classes")
public class ClassController {

    private final ListClassService listClassService;
    private final GetClassService getClassService;
    private final InsertClassService insertClassService;
    private final UpdateClassService updateClassService;
    private final DeleteClassService deleteClassService;

    @GetMapping
    public Page<ClassResponse> listClasses(Pageable pageable) {
        return listClassService.list(pageable);
    }

    @GetMapping("/{id}")
    public ClassResponse getClass(@PathVariable Long id) {
        return getClassService.byId(id);
    }

    @PostMapping
    public ClassResponse insertClass(@RequestBody UpsertClassRequest request) {
        return insertClassService.insert(request);
    }

    @PutMapping("/{id}")
    public ClassResponse updateClass(@PathVariable Long id, @RequestBody UpsertClassRequest request) {
        return updateClassService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClass(@PathVariable Long id) {
        deleteClassService.delete(id);
    }

}
