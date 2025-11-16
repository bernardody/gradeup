package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.teachersubjectclass.InsertTeacherSubjectClassRequest;
import br.com.sinodal.gradeup.controller.request.teachersubjectclass.UpdateTeacherSubjectClassRequest;
import br.com.sinodal.gradeup.controller.response.teachersubjectclass.TeacherSubjectClassResponse;
import br.com.sinodal.gradeup.service.teachersubjectclass.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher-subject-class")
public class TeacherSubjectClassController {

    private final ListTeacherSubjectClassService listTeacherSubjectClassService;
    private final GetTeacherSubjectClassService getTeacherSubjectClassService;
    private final InsertTeacherSubjectClassService insertTeacherSubjectClassService;
    private final UpdateTeacherSubjectClassService updateTeacherSubjectClassService;
    private final DeleteTeacherSubjectClassService deleteTeacherSubjectClassService;

    @GetMapping
    public Page<TeacherSubjectClassResponse> listTeacherSubjectClasses(Pageable pageable) {
        return listTeacherSubjectClassService.list(pageable);
    }

    @GetMapping("/{id}")
    public TeacherSubjectClassResponse getTeacherSubjectClass(@PathVariable Long id) {
        return getTeacherSubjectClassService.byId(id);
    }

    @PostMapping
    public TeacherSubjectClassResponse insertTeacherSubjectClass(@RequestBody InsertTeacherSubjectClassRequest request) {
        return insertTeacherSubjectClassService.insert(request);
    }

    @PutMapping("/{id}")
    public TeacherSubjectClassResponse updateTeacherSubjectClass(@PathVariable Long id, @RequestBody UpdateTeacherSubjectClassRequest request) {
        return updateTeacherSubjectClassService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacherSubjectClass(@PathVariable Long id) {
        deleteTeacherSubjectClassService.delete(id);
    }
}