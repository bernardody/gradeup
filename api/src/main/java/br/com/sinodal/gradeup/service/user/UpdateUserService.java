package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.controller.request.user.UpdateUserRequest;
import br.com.sinodal.gradeup.controller.response.user.ListUserResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateUserService {

    private final UserRepository userRepository;

    public ListUserResponse update(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return ListUserMapper.toResponse(user);
    }
}
