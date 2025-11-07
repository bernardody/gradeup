package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.controller.response.user.ListUserResponse;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;
import br.com.sinodal.gradeup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetUserService {

    private final UserRepository userRepository;

    public ListUserResponse byId(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return ListUserMapper.toResponse(user);
    }
}
