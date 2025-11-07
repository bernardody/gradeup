package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.aClass.CreateClassRequest;
import br.com.sinodal.gradeup.controller.response.aClass.ListClassResponse;
import br.com.sinodal.gradeup.service.aClass.*;
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
    private final CreateClassService createClassService;
    private final UpdateClassService updateClassService;
    private final DeleteClassService deleteClassService;

    @GetMapping
    public Page<ListClassResponse> listClasses(Pageable pageable) {
        return listClassService.list(pageable);
    }

    @GetMapping("/{id}")
    public ListClassResponse getClass(@PathVariable Long id) {
        return getClassService.byId(id);
    }

    @PostMapping
    public ListClassResponse createClass(@RequestBody CreateClassRequest request) {
        return createClassService.create(request);
    }

    @PutMapping("/{id}")
    public ListClassResponse updateClass(@PathVariable Long id, @RequestBody CreateClassRequest request) {
        return updateClassService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClass(@PathVariable Long id) {
        deleteClassService.delete(id);
    }

}
