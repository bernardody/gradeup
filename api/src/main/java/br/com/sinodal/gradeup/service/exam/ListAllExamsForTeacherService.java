package br.com.sinodal.gradeup.service.exam;

import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.exam.ListExamMapper;
import br.com.sinodal.gradeup.repository.ExamRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListAllExamsForTeacherService {

    private final AuthenticatedUserService authenticatedUserService;
    private final ExamRepository examRepository;

    public List<ExamResponse> list() {
        User loggedUser = authenticatedUserService.get();

        if (loggedUser.getType() != UserType.TEACHER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não é professor");
        }

        List<Exam> exams = examRepository.findAllByTeacherId(loggedUser.getId());

        return exams.stream()
                .map(ListExamMapper::toResponse)
                .toList();
    }
}
