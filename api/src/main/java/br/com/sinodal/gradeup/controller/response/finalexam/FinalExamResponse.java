package br.com.sinodal.gradeup.controller.response.finalexam;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinalExamResponse {

    private Long id;
    private ClassResponse classEntity;
    private SubjectResponse subject;
    private UserResponse teacher;
    private LocalDate examDate;
    private BigDecimal maxScore;
    private BigDecimal minScore;
}