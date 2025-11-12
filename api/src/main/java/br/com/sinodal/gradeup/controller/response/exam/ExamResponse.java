package br.com.sinodal.gradeup.controller.response.exam;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.controller.response.trimester.TrimesterResponse;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamResponse {

    private Long id;
    private ClassResponse classEntity;
    private SubjectResponse subject;
    private UserResponse teacher;
    private TrimesterResponse trimester;
    private String name;
    private BigDecimal maxScore;
    private LocalDate examDate;
}