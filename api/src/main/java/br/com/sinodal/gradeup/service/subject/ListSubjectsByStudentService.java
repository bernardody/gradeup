package br.com.sinodal.gradeup.service.subject;

import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.repository.SubjectRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListSubjectsByStudentService {

    private final SubjectRepository subjectRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public List<SubjectResponse> list(Long classId) {

        User loggedUser = authenticatedUserService.get();
        List<Subject> subjects;

        if (loggedUser.getType() == UserType.STUDENT) {
            subjects = subjectRepository.findSubjectByStudentIdAndClassId(loggedUser.getId(), classId);
        } else if (loggedUser.getType() == UserType.TEACHER) {
            subjects = subjectRepository.findSubjectByTeacherIdAndClassId(loggedUser.getId(), classId);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não tem matérias");

        return subjects.stream().map(ListSubjectMapper::toResponse).toList();
    }
}
