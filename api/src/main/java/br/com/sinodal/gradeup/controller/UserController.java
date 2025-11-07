package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.user.CreateUserRequest;
import br.com.sinodal.gradeup.controller.request.user.UpdateUserRequest;
import br.com.sinodal.gradeup.controller.response.user.ListUserResponse;
import br.com.sinodal.gradeup.service.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final ListUserService listUserService;
    private final GetUserService getUserService;
    private final CreateUserService createUserService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;

    @GetMapping
    public Page<ListUserResponse> listUser(Pageable pageable) {
        return listUserService.list(pageable);
    }

    @GetMapping("/{id}")
    public ListUserResponse getUser(@PathVariable Long id) {
        return getUserService.byId(id);
    }

    @PostMapping
    public ListUserResponse insertUser(@RequestBody CreateUserRequest request) {
        return createUserService.insert(request);
    }

    @PutMapping("/{id}")
    public ListUserResponse updateUSer(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return updateUserService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        deleteUserService.delete(id);
    }
}
