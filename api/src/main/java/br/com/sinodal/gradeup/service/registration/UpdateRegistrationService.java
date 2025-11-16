package br.com.sinodal.gradeup.service.registration;

import br.com.sinodal.gradeup.controller.request.registration.UpsertRegistrationRequest;
import br.com.sinodal.gradeup.controller.response.registration.RegistrationResponse;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.domain.Registration;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.registration.UpsertRegistrationMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.repository.RegistrationRepository;
import br.com.sinodal.gradeup.repository.UserRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateRegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public RegistrationResponse update(Long id, UpsertRegistrationRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar matrículas");

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        if (!student.getType().equals(UserType.STUDENT))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário informado não é um aluno");

        Clazz clazz = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        if (registrationRepository.existsByStudentIdAndClazzId(request.getStudentId(), request.getClassId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este aluno já tem está nessa turma");
        }

        registration.setStudent(student);
        registration.setClazz(clazz);

        registrationRepository.save(registration);

        return UpsertRegistrationMapper.toResponse(registration);
    }
}