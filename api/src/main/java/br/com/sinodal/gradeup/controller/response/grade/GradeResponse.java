package br.com.sinodal.gradeup.controller.response.grade;

import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GradeResponse {

    private Long id;
    private ExamResponse exam;
    private UserResponse student;
    private BigDecimal grade;
}