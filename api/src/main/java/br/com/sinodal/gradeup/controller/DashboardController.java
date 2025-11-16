package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import br.com.sinodal.gradeup.service.user.student.StudentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final AuthenticatedUserService authenticatedUserService;
    private final StudentDashboardService studentDashboardService;

    @GetMapping
    public Object getDashboard() {
        User user = authenticatedUserService.get();

        return switch (user.getType()) {
            case STUDENT -> studentDashboardService.getDashboard();
            case TEACHER -> throw new ResponseStatusException(
                    HttpStatus.NOT_IMPLEMENTED,
                    "Dashboard do professor ainda não implementado"
            );
            case ADMIN -> throw new ResponseStatusException(
                    HttpStatus.NOT_IMPLEMENTED,
                    "Dashboard do administrador ainda não implementado"
            );
        };
    }
}