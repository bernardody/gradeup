package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.FinalGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinalGradeRepository extends JpaRepository<FinalGrade, Long> {
}