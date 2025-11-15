package br.com.sinodal.gradeup.controller.response.trimester;

import br.com.sinodal.gradeup.controller.response.exam.ExamGradeResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrimesterGradesResponse {
    private Long trimesterId;
    private String trimesterName;
    private BigDecimal maxPoints;
    private BigDecimal minPoints;
    private List<ExamGradeResponse> exams;
    private BigDecimal totalScore;
    private Boolean passed;
}
