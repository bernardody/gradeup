package br.com.sinodal.gradeup.service.registration;

import br.com.sinodal.gradeup.controller.response.registration.RegistrationResponse;
import br.com.sinodal.gradeup.domain.Registration;
import br.com.sinodal.gradeup.mapper.registration.ListRegistrationMapper;
import br.com.sinodal.gradeup.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListRegistrationService {

    private final RegistrationRepository registrationRepository;

    public Page<RegistrationResponse> list(Pageable pageable) {
        Page<Registration> registrations = registrationRepository.findAll(pageable);
        return registrations.map(ListRegistrationMapper::toResponse);
    }
}
