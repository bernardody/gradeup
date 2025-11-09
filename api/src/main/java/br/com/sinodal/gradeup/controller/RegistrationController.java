package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.registration.UpsertRegistrationRequest;
import br.com.sinodal.gradeup.controller.response.registration.RegistrationResponse;
import br.com.sinodal.gradeup.service.registration.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final ListRegistrationService listRegistrationService;
    private final GetRegistrationService getRegistrationService;
    private final InsertRegistrationService insertRegistrationService;
    private final UpdateRegistrationService updateRegistrationService;
    private final DeleteRegistrationService deleteRegistrationService;

    @GetMapping
    public Page<RegistrationResponse> listRegistration(Pageable pageable) {
        return listRegistrationService.list(pageable);
    }

    @GetMapping("/{id}")
    public RegistrationResponse getRegistration(@PathVariable Long id) {
        return getRegistrationService.byId(id);
    }

    @PostMapping
    public RegistrationResponse insertRegistration(@RequestBody UpsertRegistrationRequest request) {
        return insertRegistrationService.insert(request);
    }

    @PutMapping("/{id}")
    public RegistrationResponse updateRegistration(@PathVariable Long id, @RequestBody UpsertRegistrationRequest request) {
        return updateRegistrationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRegistration(@PathVariable Long id) {
        deleteRegistrationService.delete(id);
    }
}
