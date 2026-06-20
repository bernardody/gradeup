package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByStudentIdAndClazzId(Long studentId, Long classId);

    Optional<Registration> findByStudentId(Long id);

    List<Registration> findByClazzId(Long classId);
}
