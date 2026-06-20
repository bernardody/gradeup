package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Trimester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrimesterRepository extends JpaRepository<Trimester, Long> {
}