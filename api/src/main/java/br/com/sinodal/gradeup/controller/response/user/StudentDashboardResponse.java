package br.com.sinodal.gradeup.controller.response.user;

import br.com.sinodal.gradeup.controller.response.classes.ClassInfoResponse;
import br.com.sinodal.gradeup.controller.response.subject.SubjectGradesResponse;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDashboardResponse {
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private ClassInfoResponse classInfo;
    private List<SubjectGradesResponse> subjects;
}
