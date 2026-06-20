package br.com.sinodal.gradeup.service.exam;

import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.exam.ListExamsTeacher;
import br.com.sinodal.gradeup.repository.ExamRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListExamsForTeacherService {

    private final AuthenticatedUserService authenticatedUserService;
    private final ExamRepository examRepository;


    public List<ExamResponse> list(Long classId, Long subjectId, Long trimesterId) {

        User loggedUser = authenticatedUserService.get();

        if (loggedUser.getType() != UserType.TEACHER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não é estudante");
        }

        List<Exam> exams = examRepository.findByClassAndSubjectAndTrimester(classId, subjectId, trimesterId);

        return exams.stream().map(ListExamsTeacher::toResponse).toList();
    }
}
