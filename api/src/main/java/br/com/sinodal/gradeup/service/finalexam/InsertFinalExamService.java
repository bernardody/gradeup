package br.com.sinodal.gradeup.service.finalexam;

import br.com.sinodal.gradeup.controller.request.finalexam.InsertFinalExamRequest;
import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamResponse;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.domain.FinalExam;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.finalexam.InsertFinalExamMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.repository.FinalExamRepository;
import br.com.sinodal.gradeup.repository.SubjectRepository;
import br.com.sinodal.gradeup.repository.UserRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class InsertFinalExamService {

    private final FinalExamRepository finalExamRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public FinalExamResponse insert(InsertFinalExamRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar provas finais");

        Clazz clazz = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matéria não encontrada"));

        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));

        if (!teacher.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário informado não é um professor");

        FinalExam finalExam = InsertFinalExamMapper.toEntity(request, clazz, subject, teacher);

        finalExamRepository.save(finalExam);

        return InsertFinalExamMapper.toResponse(finalExam);
    }
}