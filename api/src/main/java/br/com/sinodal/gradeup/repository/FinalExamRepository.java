package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.FinalExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinalExamRepository extends JpaRepository<FinalExam, Long> {
    List<FinalExam> findByClazzId(Long id);
}