package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepository extends JpaRepository<Clazz, Long> {

    @Query("""
            SELECT DISTINCT c FROM Clazz c
            JOIN TeacherSubjectClass tsc ON tsc.clazz.id = c.id
            WHERE tsc.teacher.id = :teacherId
            """)
    List<Clazz> findClassesByTeacherId(@Param("teacherId") Long teacherId);

    @Query("""
            SELECT DISTINCT c FROM Clazz c
            JOIN Registration r ON r.clazz.id = c.id
            WHERE r.student.id = :studentId
            """)
    List<Clazz> findClassesByStudentId(@Param("studentId") Long studentId);
}
