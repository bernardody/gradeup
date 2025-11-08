package br.com.sinodal.gradeup.service.subject;

import br.com.sinodal.gradeup.controller.request.subject.CreateSubjectRequest;
import br.com.sinodal.gradeup.controller.response.subject.ListSubjectResponse;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.subject.UpdateSubjectMapper;
import br.com.sinodal.gradeup.repository.SubjectRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateSubjectService {

    private final SubjectRepository subjectRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ListSubjectResponse update(Long id, CreateSubjectRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar essa matéria");

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matéria não encontrada"));

        subject.setName(request.getName());

        subjectRepository.save(subject);

        return UpdateSubjectMapper.toResponse(subject);
    }
}