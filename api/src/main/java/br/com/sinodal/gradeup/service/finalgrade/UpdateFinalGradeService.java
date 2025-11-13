package br.com.sinodal.gradeup.service.finalgrade;

import br.com.sinodal.gradeup.controller.request.finalgrade.UpdateFinalGradeRequest;
import br.com.sinodal.gradeup.controller.response.finalgrade.FinalGradeResponse;
import br.com.sinodal.gradeup.domain.FinalGrade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.finalgrade.UpdateFinalGradeMapper;
import br.com.sinodal.gradeup.repository.FinalGradeRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateFinalGradeService {

    private final FinalGradeRepository finalGradeRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public FinalGradeResponse update(Long id, UpdateFinalGradeRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar notas finais");

        FinalGrade finalGrade = finalGradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota final não encontrada"));

        finalGrade.setGrade(request.getGrade());

        finalGradeRepository.save(finalGrade);

        return UpdateFinalGradeMapper.toResponse(finalGrade);
    }
}