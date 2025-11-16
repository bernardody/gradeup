package br.com.sinodal.gradeup.controller.response.exam;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamGradeResponse {
    private Long examId;
    private String examName;
    private LocalDate examDate;
    private BigDecimal maxScore;
    private BigDecimal studentGrade;
    private BigDecimal percentage;
}
