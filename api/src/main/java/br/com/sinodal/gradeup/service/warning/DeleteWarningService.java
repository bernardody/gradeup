package br.com.sinodal.gradeup.service.warning;

import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.repository.WarningRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class DeleteWarningService {

    private final WarningRepository warningRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public void delete(Long id) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir avisos");

        Warning warning = warningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso não encontrado"));

        warningRepository.deleteById(warning.getId());
    }
}
