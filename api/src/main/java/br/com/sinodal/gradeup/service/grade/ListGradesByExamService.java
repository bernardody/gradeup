package br.com.sinodal.gradeup.service.grade;

import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.grade.ListGradeMapper;
import br.com.sinodal.gradeup.repository.ExamRepository;
import br.com.sinodal.gradeup.repository.GradeRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListGradesByExamService {

    private final AuthenticatedUserService authenticatedUserService;
    private final GradeRepository gradeRepository;
    private final ExamRepository examRepository;

    public List<GradeResponse> list(Long examId) {

        User loggedUser = authenticatedUserService.get();

        if (loggedUser.getType() != UserType.TEACHER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas professores podem visualizar as notas");
        }

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));

        if (!exam.getTeacher().getId().equals(loggedUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para visualizar as notas deste exame");
        }

        List<Grade> grades = gradeRepository.findByExamIdOrderByStudentNameAsc(examId);

        return grades.stream()
                .map(ListGradeMapper::toResponse)
                .toList();
    }
}
