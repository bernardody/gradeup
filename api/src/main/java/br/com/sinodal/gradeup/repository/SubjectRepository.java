package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("""
            SELECT DISTINCT s FROM Subject s
            JOIN TeacherSubjectClass tsc ON tsc.subject.id = s.id
            JOIN Registration r ON r.clazz.id = tsc.clazz.id
            WHERE r.student.id = :studentId
            ORDER BY s.id
            """)
    List<Subject> findSubjectsByStudentId(@Param("studentId") Long studentId);

    @Query("""
            SELECT DISTINCT s FROM Subject s
            JOIN TeacherSubjectClass tsc ON tsc.subject.id = s.id
            WHERE tsc.teacher.id = :teacherId
            ORDER BY s.id
            """)
    List<Subject> findSubjectsByTeacherId(@Param("teacherId") Long teacherId);
}