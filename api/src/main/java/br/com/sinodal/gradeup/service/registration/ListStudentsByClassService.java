package br.com.sinodal.gradeup.service.registration;

import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import br.com.sinodal.gradeup.domain.Registration;
import br.com.sinodal.gradeup.mapper.user.ListUserMapper;
import br.com.sinodal.gradeup.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListStudentsByClassService {

    private final RegistrationRepository registrationRepository;

    public List<UserResponse> list(Long classId) {
        List<Registration> registrations = registrationRepository.findByClazzId(classId);
        return registrations.stream()
                .map(registration -> ListUserMapper.toResponse(registration.getStudent()))
                .collect(Collectors.toList());
    }
}
