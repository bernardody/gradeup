package br.com.sinodal.gradeup.service.aClass;

import br.com.sinodal.gradeup.controller.request.aClass.CreateClassRequest;
import br.com.sinodal.gradeup.controller.response.aClass.ListClassResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.aClass.CreateClassMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CreateClassService {

    private final ClassRepository classRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ListClassResponse create(CreateClassRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar turmas");

        Class aClass = CreateClassMapper.toEntity(request);

        classRepository.save(aClass);

        return CreateClassMapper.toResponse(aClass);
    }
}
