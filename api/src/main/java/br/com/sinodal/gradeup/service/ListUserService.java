package br.com.sinodal.gradeup.service;

import br.com.sinodal.gradeup.controller.response.ListUserResponse;
import br.com.sinodal.gradeup.domain.Users;
import br.com.sinodal.gradeup.mapper.ListUserMapper;
import br.com.sinodal.gradeup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListUserService {

    private final UserRepository userRepository;

    public Page<ListUserResponse> list(Pageable pageable) {
        Page<Users> users = userRepository.findAll(pageable);
        return users.map(ListUserMapper::toResponse);
    }
}
