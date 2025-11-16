package br.com.sinodal.gradeup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "Subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "subject")
    @Builder.Default
    @JsonIgnore
    private List<Exam> exams = new ArrayList<>();

    @OneToMany(mappedBy = "subject")
    @Builder.Default
    @JsonIgnore
    private List<FinalExam> finalExams = new ArrayList<>();

    @OneToMany(mappedBy = "subject")
    @Builder.Default
    @JsonIgnore
    private List<TeacherSubjectClass> teacherAssignments = new ArrayList<>();
}
