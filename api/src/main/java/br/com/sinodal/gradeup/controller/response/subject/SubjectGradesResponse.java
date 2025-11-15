package br.com.sinodal.gradeup.controller.response.subject;

import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamGradeResponse;
import br.com.sinodal.gradeup.controller.response.trimester.TrimesterGradesResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectGradesResponse {
    private Long subjectId;
    private String subjectName;
    private List<TrimesterGradesResponse> trimesters;
    private FinalExamGradeResponse finalExam;
    private BigDecimal average;
}