package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByClazzId(Long id);
}