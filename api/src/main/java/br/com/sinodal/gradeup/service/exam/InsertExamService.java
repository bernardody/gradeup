package br.com.sinodal.gradeup.service.exam;

import br.com.sinodal.gradeup.controller.request.exam.InsertExamRequest;
import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.Trimester;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.exam.InsertExamMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.repository.ExamRepository;
import br.com.sinodal.gradeup.repository.SubjectRepository;
import br.com.sinodal.gradeup.repository.TrimesterRepository;
import br.com.sinodal.gradeup.repository.UserRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class InsertExamService {

    private final ExamRepository examRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final TrimesterRepository trimesterRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ExamResponse insert(InsertExamRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar provas");

        Class classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matéria não encontrada"));

        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));

        if (!teacher.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário informado não é um professor");

        Trimester trimester = trimesterRepository.findById(request.getTrimesterId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trimestre não encontrado"));

        Exam exam = InsertExamMapper.toEntity(request, classEntity, subject, teacher, trimester);

        examRepository.save(exam);

        return InsertExamMapper.toResponse(exam);
    }
}