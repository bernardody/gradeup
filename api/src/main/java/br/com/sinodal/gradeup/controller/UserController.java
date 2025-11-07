package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.response.user.ListUserResponse;
import br.com.sinodal.gradeup.service.user.ListUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final ListUserService listUserService;

    @GetMapping
    public Page<ListUserResponse> listUser(Pageable pageable) {
        return listUserService.list(pageable);
    }
}
