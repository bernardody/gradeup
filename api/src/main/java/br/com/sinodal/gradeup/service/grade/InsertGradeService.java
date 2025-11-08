package br.com.sinodal.gradeup.service.grade;

import br.com.sinodal.gradeup.controller.request.grade.InsertGradeRequest;
import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.grade.InsertGradeMapper;
import br.com.sinodal.gradeup.repository.ExamRepository;
import br.com.sinodal.gradeup.repository.GradeRepository;
import br.com.sinodal.gradeup.repository.UserRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import br.com.sinodal.gradeup.validator.GradeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class InsertGradeService {

    private final GradeRepository gradeRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final GradeValidator gradeValidator;

    public GradeResponse insert(InsertGradeRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar notas");

        gradeValidator.validate(request.getGrade());

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));

        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        if (!student.getType().equals(UserType.STUDENT))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário informado não é um aluno");

        Grade grade = InsertGradeMapper.toEntity(request, exam, student);

        gradeRepository.save(grade);

        return InsertGradeMapper.toResponse(grade);
    }
}