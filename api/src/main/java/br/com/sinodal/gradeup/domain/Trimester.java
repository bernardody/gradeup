package br.com.sinodal.gradeup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "Trimesters")
public class Trimester {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal maxPoints;

    @NotNull
    private BigDecimal minPoints;

    @OneToMany(mappedBy = "trimester")
    @Builder.Default
    @JsonIgnore
    private List<Exam> exams = new ArrayList<>();
}