package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.TeacherSubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherSubjectClassRepository extends JpaRepository<TeacherSubjectClass, Long> {
    boolean existsByTeacherIdAndSubjectIdAndClassEntityId(Long teacherId, Long subjectId, Long classId);
}