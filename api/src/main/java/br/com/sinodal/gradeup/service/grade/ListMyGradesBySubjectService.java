package br.com.sinodal.gradeup.service.grade;

import br.com.sinodal.gradeup.controller.response.grade.GradeByStudentResponse;
import br.com.sinodal.gradeup.controller.response.grade.GradeResponse;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.grade.ListGradeByStudentMapper;
import br.com.sinodal.gradeup.mapper.grade.ListGradeMapper;
import br.com.sinodal.gradeup.repository.GradeRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListMyGradesBySubjectService {

    private final GradeRepository gradeRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public List<GradeByStudentResponse> list(Long subjectId) {
        User loggedUser = authenticatedUserService.get();

        if (loggedUser.getType() != UserType.STUDENT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não é um estudante");
        }

        List<Grade> grades = gradeRepository.findGradesByStudentIdAndSubjectId(loggedUser.getId(), subjectId);

        return grades.stream().map(ListGradeByStudentMapper::toResponse).toList();
    }
}
