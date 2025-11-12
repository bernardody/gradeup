package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.trimester.InsertTrimesterRequest;
import br.com.sinodal.gradeup.controller.request.trimester.UpdateTrimesterRequest;
import br.com.sinodal.gradeup.controller.response.trimester.TrimesterResponse;
import br.com.sinodal.gradeup.service.trimester.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trimesters")
public class TrimesterController {

    private final ListTrimesterService listTrimesterService;
    private final GetTrimesterService getTrimesterService;
    private final InsertTrimesterService insertTrimesterService;
    private final UpdateTrimesterService updateTrimesterService;
    private final DeleteTrimesterService deleteTrimesterService;

    @GetMapping
    public Page<TrimesterResponse> listTrimesters(Pageable pageable) {
        return listTrimesterService.list(pageable);
    }

    @GetMapping("/{id}")
    public TrimesterResponse getTrimester(@PathVariable Long id) {
        return getTrimesterService.byId(id);
    }

    @PostMapping
    public TrimesterResponse insertTrimester(@RequestBody InsertTrimesterRequest request) {
        return insertTrimesterService.insert(request);
    }

    @PutMapping("/{id}")
    public TrimesterResponse updateTrimester(@PathVariable Long id, @RequestBody UpdateTrimesterRequest request) {
        return updateTrimesterService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrimester(@PathVariable Long id) {
        deleteTrimesterService.delete(id);
    }
}