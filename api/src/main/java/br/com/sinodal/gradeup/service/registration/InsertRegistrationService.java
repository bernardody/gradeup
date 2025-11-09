package br.com.sinodal.gradeup.service.registration;

import br.com.sinodal.gradeup.controller.request.registration.UpsertRegistrationRequest;
import br.com.sinodal.gradeup.controller.response.registration.RegistrationResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Registration;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.registration.UpsertRegistrationMapper;
import br.com.sinodal.gradeup.mapper.warning.InsertWarningMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.repository.RegistrationRepository;
import br.com.sinodal.gradeup.repository.UserRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class InsertRegistrationService {

    private final RegistrationRepository registrationRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    public RegistrationResponse insert(UpsertRegistrationRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar matrículas");

        User student = userRepository.findById(request.getStudent_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        Class classEntity = classRepository.findById(request.getClass_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        Registration registration = UpsertRegistrationMapper.toEntity(request, student, classEntity);

        registrationRepository.save(registration);

        return UpsertRegistrationMapper.toResponse(registration);
    }
}
