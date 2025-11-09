package br.com.sinodal.gradeup.service.warning;

import br.com.sinodal.gradeup.controller.request.warning.UpdateWarningRequest;
import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.warning.UpdateWarningMapper;
import br.com.sinodal.gradeup.repository.WarningRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateWarningService {

    private final WarningRepository warningRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public WarningResponse update(Long id, UpdateWarningRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar provas");

        Warning warning = warningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso não encontrado"));

        warning.setTitle(request.getTitle());
        warning.setContent(request.getContent());

        warningRepository.save(warning);

        return UpdateWarningMapper.toResponse(warning);
    }
}
