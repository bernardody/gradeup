package br.com.sinodal.gradeup.controller.response.exam;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamGradeByStudentResponse {

    private Long examId;
    private Long classId;
    private Long subjectId;
    private Long teacherId;
    private Long trimesterId;
    private String examName;
    private BigDecimal maxScore;
    private LocalDate examDate;
}
