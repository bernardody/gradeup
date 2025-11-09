package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Warning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningRepository extends JpaRepository<Warning, Long> {
}
