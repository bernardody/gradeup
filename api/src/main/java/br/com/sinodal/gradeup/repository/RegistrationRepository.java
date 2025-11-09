package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
