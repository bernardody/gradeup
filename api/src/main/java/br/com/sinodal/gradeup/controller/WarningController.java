package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.warning.InsertWarningRequest;
import br.com.sinodal.gradeup.controller.request.warning.UpdateWarningRequest;
import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.service.warning.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/warnings")
public class WarningController {

    private final ListWarningService listWarningService;
    private final GetWarningService getWarningService;
    private final InsertWarningsService insertWarningsService;
    private final UpdateWarningService updateWarningService;
    private final DeleteWarningService deleteWarningService;

    @GetMapping
    public Page<WarningResponse> listWarnings(Pageable pageable) {
        return listWarningService.list(pageable);
    }

    @GetMapping("/{id}")
    public WarningResponse getWarning(@PathVariable Long id) {
        return getWarningService.byId(id);
    }

    @PostMapping
    public WarningResponse insertWarning(@RequestBody InsertWarningRequest request) {
        return insertWarningsService.insert(request);
    }

    @PutMapping("/{id}")
    public WarningResponse updateWarning(@PathVariable Long id, @RequestBody UpdateWarningRequest request) {
        return updateWarningService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWarnings(@PathVariable Long id) {
        deleteWarningService.delete(id);
    }
}
