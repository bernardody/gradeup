package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    boolean existsByExamIdAndStudentId(Long examId, Long studentId);

    List<Grade> findByStudentId(Long id);

    @Query("""
            SELECT g FROM Grade g
            JOIN FETCH g.exam e
            JOIN FETCH e.subject s
            JOIN FETCH e.clazz c
            JOIN FETCH e.trimester t
            WHERE g.student.id = :studentId AND e.subject.id = :subjectId
            ORDER BY e.examDate DESC
            """)
    List<Grade> findGradesByStudentIdAndSubjectId(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);
}