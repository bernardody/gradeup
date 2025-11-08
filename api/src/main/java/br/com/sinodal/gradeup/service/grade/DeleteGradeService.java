package br.com.sinodal.gradeup.service.grade;

import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.repository.GradeRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class DeleteGradeService {

    private final GradeRepository gradeRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public void delete(Long id) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar notas");

        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada"));

        gradeRepository.deleteById(grade.getId());
    }
}