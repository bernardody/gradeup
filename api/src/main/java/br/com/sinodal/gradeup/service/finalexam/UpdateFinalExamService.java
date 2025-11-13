package br.com.sinodal.gradeup.service.finalexam;

import br.com.sinodal.gradeup.controller.request.finalexam.UpdateFinalExamRequest;
import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.FinalExam;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.finalexam.UpdateFinalExamMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.repository.FinalExamRepository;
import br.com.sinodal.gradeup.repository.SubjectRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateFinalExamService {

    private final FinalExamRepository finalExamRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public FinalExamResponse update(Long id, UpdateFinalExamRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar provas finais");

        FinalExam finalExam = finalExamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova final não encontrada"));

        Class classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matéria não encontrada"));

        finalExam.setClassEntity(classEntity);
        finalExam.setSubject(subject);
        finalExam.setExamDate(request.getExamDate());
        finalExam.setMaxScore(request.getMaxScore());
        finalExam.setMinScore(request.getMinScore());

        finalExamRepository.save(finalExam);

        return UpdateFinalExamMapper.toResponse(finalExam);
    }
}