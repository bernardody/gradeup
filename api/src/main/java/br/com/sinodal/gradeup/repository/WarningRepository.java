package br.com.sinodal.gradeup.repository;

import br.com.sinodal.gradeup.domain.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarningRepository extends JpaRepository<Warning, Long> {

    @Query("SELECT w FROM Warning w " +
            "JOIN FETCH w.exam e " +
            "JOIN FETCH e.clazz c " +
            "WHERE c.id = :classId " +
            "ORDER BY w.createdAt DESC")
    List<Warning> findByClassIdForStudent(@Param("classId") Long classId);

    @Query("SELECT w FROM Warning w " +
            "JOIN FETCH w.exam e " +
            "JOIN FETCH e.clazz c " +
            "JOIN FETCH e.teacher t " +
            "WHERE c.id = :classId " +
            "AND t.id = :teacherId " +
            "ORDER BY w.createdAt DESC")
    List<Warning> findByClassIdAndTeacherId(
            @Param("classId") Long classId,
            @Param("teacherId") Long teacherId);
}
