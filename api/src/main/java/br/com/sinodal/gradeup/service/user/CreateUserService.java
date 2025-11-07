package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.controller.request.user.CreateUserRequest;
import br.com.sinodal.gradeup.controller.response.user.ListUserResponse;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.user.InsertUserMapper;
import br.com.sinodal.gradeup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUserService {

    private final UserRepository userRepository;

    public ListUserResponse insert(CreateUserRequest request) {

        User user = InsertUserMapper.toEntity(request);

        userRepository.save(user);

        return InsertUserMapper.toResponse(user);
    }
}
