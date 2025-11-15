package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Grade;
import br.com.sinodal.gradeup.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    boolean existsByExamIdAndStudentId(Long examId, Long studentId);

    List<Grade> findByStudentId(Long id);
}