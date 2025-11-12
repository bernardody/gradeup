package br.com.sinodal.gradeup.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "Final_Grades")
public class FinalGrade {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "final_exam_id", nullable = false)
    private FinalExam finalExam;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @NotNull
    private BigDecimal grade;
}