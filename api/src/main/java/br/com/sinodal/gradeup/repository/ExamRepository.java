package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}