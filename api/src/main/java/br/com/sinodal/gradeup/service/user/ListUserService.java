package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;
import br.com.sinodal.gradeup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListUserService {

    private final UserRepository userRepository;

    public Page<UserResponse> list(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(ListUserMapper::toResponse);
    }
}
