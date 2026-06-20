package br.com.sinodal.gradeup.service.grade;

import br.com.sinodal.gradeup.controller.request.grade.UpdateGradeRequest;
import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.grade.UpdateGradeMapper;
import br.com.sinodal.gradeup.repository.GradeRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import br.com.sinodal.gradeup.validator.GradeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateGradeService {

    private final GradeRepository gradeRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final GradeValidator gradeValidator;

    public GradeResponse update(Long id, UpdateGradeRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar notas");

        gradeValidator.validate(request.getGrade());

        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada"));

        grade.setGrade(request.getGrade());

        gradeRepository.save(grade);

        return UpdateGradeMapper.toResponse(grade);
    }
}