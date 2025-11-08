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
@Table(name = "Grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @NotNull
    private BigDecimal grade;
}