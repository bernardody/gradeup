package br.com.sinodal.gradeup.service.finalgrade;

import br.com.sinodal.gradeup.controller.request.finalgrade.InsertFinalGradeRequest;
import br.com.sinodal.gradeup.controller.response.finalgrade.FinalGradeResponse;
import br.com.sinodal.gradeup.domain.FinalExam;
import br.com.sinodal.gradeup.domain.FinalGrade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.finalgrade.InsertFinalGradeMapper;
import br.com.sinodal.gradeup.repository.FinalExamRepository;
import br.com.sinodal.gradeup.repository.FinalGradeRepository;
import br.com.sinodal.gradeup.repository.UserRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class InsertFinalGradeService {

    private final FinalGradeRepository finalGradeRepository;
    private final FinalExamRepository finalExamRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public FinalGradeResponse insert(InsertFinalGradeRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para lançar notas finais");

        FinalExam finalExam = finalExamRepository.findById(request.getFinalExamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova final não encontrada"));

        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        if (!student.getType().equals(UserType.STUDENT))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário informado não é um aluno");

        if (finalGradeRepository.existsByFinalExamIdAndStudentId(request.getFinalExamId(), request.getStudentId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este aluno já está inscrito na prova");
        }

        FinalGrade finalGrade = InsertFinalGradeMapper.toEntity(request, finalExam, student);

        finalGradeRepository.save(finalGrade);

        return InsertFinalGradeMapper.toResponse(finalGrade);
    }
}