package br.com.sinodal.gradeup.service.finalgrade;

import br.com.sinodal.gradeup.domain.FinalGrade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.repository.FinalGradeRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class DeleteFinalGradeService {

    private final FinalGradeRepository finalGradeRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public void delete(Long id) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar notas finais");

        FinalGrade finalGrade = finalGradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota final não encontrada"));

        finalGradeRepository.deleteById(finalGrade.getId());
    }
}