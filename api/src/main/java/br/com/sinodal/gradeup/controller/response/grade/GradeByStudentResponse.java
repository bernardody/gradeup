package br.com.sinodal.gradeup.controller.response.grade;

import br.com.sinodal.gradeup.controller.response.exam.ExamGradeByStudentResponse;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GradeByStudentResponse {

    private Long gradeId;
    private ExamGradeByStudentResponse exam;
    private Long studentId;
    private BigDecimal grade;
}
