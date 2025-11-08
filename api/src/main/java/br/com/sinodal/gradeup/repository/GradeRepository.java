package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}