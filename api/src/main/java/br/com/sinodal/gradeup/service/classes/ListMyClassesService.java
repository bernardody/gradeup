package br.com.sinodal.gradeup.service.classes;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListMyClassesService {

    private final ClassRepository classRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public List<ClassResponse> list() {

        User loggedUser = authenticatedUserService.get();
        List<Clazz> classes;

        if (loggedUser.getType() == UserType.STUDENT) {
            classes = classRepository.findClassesByStudentId(loggedUser.getId());
        } else if (loggedUser.getType() == UserType.TEACHER) {
            classes = classRepository.findClassesByTeacherId(loggedUser.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não tem turmas");
        }

        return classes.stream().map(ListClassMapper::toResponse).toList();
    }
}
