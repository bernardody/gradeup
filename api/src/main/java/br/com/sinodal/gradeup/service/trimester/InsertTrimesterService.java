package br.com.sinodal.gradeup.service.trimester;

import br.com.sinodal.gradeup.controller.request.trimester.InsertTrimesterRequest;
import br.com.sinodal.gradeup.controller.response.trimester.TrimesterResponse;
import br.com.sinodal.gradeup.domain.Trimester;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.trimester.InsertTrimesterMapper;
import br.com.sinodal.gradeup.repository.TrimesterRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class InsertTrimesterService {

    private final TrimesterRepository trimesterRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public TrimesterResponse insert(InsertTrimesterRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar trimestres");

        Trimester trimester = InsertTrimesterMapper.toEntity(request);

        trimesterRepository.save(trimester);

        return InsertTrimesterMapper.toResponse(trimester);
    }
}