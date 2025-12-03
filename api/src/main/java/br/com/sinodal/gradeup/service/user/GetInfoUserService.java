package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetInfoUserService {

    private final AuthenticatedUserService authenticatedUserService;

    public UserResponse get() {

        User loggedUser = authenticatedUserService.get();

        return ListUserMapper.toResponse(loggedUser);
    }
}
