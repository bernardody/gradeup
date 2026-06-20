package br.com.sinodal.gradeup.controller.request.teachersubjectclass;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertTeacherSubjectClassRequest {

    @NotNull
    private Long teacherId;

    @NotNull
    private Long subjectId;

    @NotNull
    private Long classId;
}