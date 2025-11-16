package br.com.sinodal.gradeup.controller.response.finalexam;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinalExamGradeResponse {
    private Long finalExamId;
    private LocalDate examDate;
    private BigDecimal maxScore;
    private BigDecimal minScore;
    private BigDecimal studentGrade;
    private Boolean passed;
}
