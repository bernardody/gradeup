package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByClazzId(Long id);

    @Query("SELECT e FROM Exam e " +
            "JOIN FETCH e.clazz c " +
            "JOIN FETCH e.subject s " +
            "JOIN FETCH e.teacher t " +
            "JOIN FETCH e.trimester tr " +
            "WHERE c.id = :classId " +
            "AND s.id = :subjectId " +
            "AND tr.id = :trimesterId " +
            "ORDER BY e.examDate ASC")
    List<Exam> findByClassAndSubjectAndTrimester(
            @Param("classId") Long classId,
            @Param("subjectId") Long subjectId,
            @Param("trimesterId") Long trimesterId);
}