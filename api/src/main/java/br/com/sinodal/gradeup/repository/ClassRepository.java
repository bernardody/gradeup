package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Clazz, Long> {
}
