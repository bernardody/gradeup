package br.com.sinodal.gradeup.controller.response.finalgrade;

import br.com.sinodal.gradeup.controller.response.finalexam.FinalExamResponse;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinalGradeResponse {

    private Long id;
    private FinalExamResponse finalExam;
    private UserResponse student;
    private BigDecimal grade;
}