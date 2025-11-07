package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, Long> {
}
