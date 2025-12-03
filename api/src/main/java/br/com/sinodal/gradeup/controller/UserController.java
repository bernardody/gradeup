package br.com.sinodal.gradeup.controller;

import br.com.sinodal.gradeup.controller.request.user.InsertUserRequest;
import br.com.sinodal.gradeup.controller.request.user.UpdateUserRequest;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
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
    private final GetInfoUserService getInfoUserService;
    private final GetUserService getUserService;
    private final InsertUserService createUserService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;

    @GetMapping
    public Page<UserResponse> listUser(Pageable pageable) {
        return listUserService.list(pageable);
    }

    @GetMapping("/my-info")
    public UserResponse getInfo() {
        return getInfoUserService.get();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return getUserService.byId(id);
    }

    @PostMapping
    public UserResponse insertUser(@RequestBody InsertUserRequest request) {
        return createUserService.insert(request);
    }

    @PutMapping("/{id}")
    public UserResponse updateUSer(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return updateUserService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        deleteUserService.delete(id);
    }
}
