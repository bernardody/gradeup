package br.com.sinodal.gradeup.service.warning;

import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.warning.ListWarningMapper;
import br.com.sinodal.gradeup.repository.WarningRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListWarningsByStudentService {

    private final AuthenticatedUserService authenticatedUserService;
    private final WarningRepository warningRepository;


    public List<WarningResponse> list(Long classId) {
        User loggedUser = authenticatedUserService.get();

        if (loggedUser.getType() != UserType.STUDENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas estudantes podem visualizar seus warnings");
        }

        List<Warning> warnings = warningRepository.findByClassIdForStudent(classId);

        return warnings.stream()
                .map(ListWarningMapper::toResponse)
                .toList();
    }
}
