package br.com.sinodal.gradeup.service.registration;

import br.com.sinodal.gradeup.controller.response.registration.RegistrationResponse;
import br.com.sinodal.gradeup.domain.Registration;
import br.com.sinodal.gradeup.mapper.registration.ListRegistrationMapper;
import br.com.sinodal.gradeup.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetRegistrationService {

    private final RegistrationRepository registrationRepository;


    public RegistrationResponse byId(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        return ListRegistrationMapper.toResponse(registration);
    }
}
