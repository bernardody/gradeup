package br.com.sinodal.gradeup.service.classes;

import br.com.sinodal.gradeup.controller.request.classes.UpsertClassRequest;
import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.classes.InsertClassMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class InsertClassService {

    private final ClassRepository classRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ClassResponse insert(UpsertClassRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar turmas");

        Clazz clazz = InsertClassMapper.toEntity(request);

        classRepository.save(clazz);

        return InsertClassMapper.toResponse(clazz);
    }
}
