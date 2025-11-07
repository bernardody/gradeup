package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class DeleteUserService {

    private final UserRepository userRepository;


    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        userRepository.deleteById(user.getId());
    }
}
