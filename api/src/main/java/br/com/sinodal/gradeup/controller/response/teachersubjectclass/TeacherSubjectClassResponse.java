package br.com.sinodal.gradeup.controller.response.teachersubjectclass;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.controller.response.user.UserResponse;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherSubjectClassResponse {

    private Long id;
    private UserResponse teacher;
    private SubjectResponse subject;
    private ClassResponse classEntity;
}