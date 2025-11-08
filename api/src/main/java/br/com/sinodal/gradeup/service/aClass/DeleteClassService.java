package br.com.sinodal.gradeup.service.aClass;

import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class DeleteClassService {

    private final ClassRepository classRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public void delete(Long id) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar essa turma");

        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        classRepository.deleteById(aClass.getId());
    }
}
