package br.com.sinodal.gradeup.service.trimester;

import br.com.sinodal.gradeup.controller.request.trimester.UpdateTrimesterRequest;
import br.com.sinodal.gradeup.controller.response.trimester.TrimesterResponse;
import br.com.sinodal.gradeup.domain.Trimester;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.trimester.UpdateTrimesterMapper;
import br.com.sinodal.gradeup.repository.TrimesterRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateTrimesterService {

    private final TrimesterRepository trimesterRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public TrimesterResponse update(Long id, UpdateTrimesterRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar trimestres");

        Trimester trimester = trimesterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trimestre não encontrado"));

        trimester.setName(request.getName());
        trimester.setMaxPoints(request.getMaxPoints());
        trimester.setMinPoints(request.getMinPoints());

        trimesterRepository.save(trimester);

        return UpdateTrimesterMapper.toResponse(trimester);
    }
}