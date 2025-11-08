package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.controller.request.user.CreateUserRequest;
import br.com.sinodal.gradeup.controller.response.user.ListUserResponse;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.user.InsertUserMapper;
import br.com.sinodal.gradeup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ListUserResponse insert(CreateUserRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar usuários");

        User user = InsertUserMapper.toEntity(request);

        userRepository.save(user);

        return InsertUserMapper.toResponse(user);
    }
}
