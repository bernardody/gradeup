package br.com.sinodal.gradeup.service.warning;

import br.com.sinodal.gradeup.controller.request.warning.InsertWarningRequest;
import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.domain.*;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.warning.InsertWarningMapper;
import br.com.sinodal.gradeup.repository.*;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class InsertWarningsService {

    private final WarningRepository warningRepository;
    private final ExamRepository examRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public WarningResponse insert(InsertWarningRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar avisos");

        Exam exam = examRepository.findById(request.getExam_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));

        Warning warning = InsertWarningMapper.toEntity(request, exam);
        warning.setCreatedAt(LocalDateTime.now());

        warningRepository.save(warning);

        return InsertWarningMapper.toResponse(warning);
    }
}
