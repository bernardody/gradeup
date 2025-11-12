package br.com.sinodal.gradeup.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "Final_Exams")
public class FinalExam {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class classEntity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @NotNull
    @Column(name = "exam_date")
    private LocalDate examDate;

    @NotNull
    private BigDecimal maxScore;

    @NotNull
    private BigDecimal minScore;
}