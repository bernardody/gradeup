package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}