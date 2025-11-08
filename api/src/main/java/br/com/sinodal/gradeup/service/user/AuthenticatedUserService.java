package br.com.sinodal.gradeup.service.user;

import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class AuthenticatedUserService {
    private final UserRepository userRepository;

    public AuthenticatedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        return jwt.getClaim("email");
    }

    public User get() {
        return userRepository.findByEmail(getEmail())
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "Usuário não existe ou não está autenticado"));
    }
}
