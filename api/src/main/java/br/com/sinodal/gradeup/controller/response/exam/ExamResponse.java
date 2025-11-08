package br.com.sinodal.gradeup.controller.response.exam;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import lombok.*;

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
    private String name;
    private LocalDate examDate;
}