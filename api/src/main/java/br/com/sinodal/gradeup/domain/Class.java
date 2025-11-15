package br.com.sinodal.gradeup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "Classes")
public class Class {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private int year;

    @OneToMany(mappedBy = "classEntity")
    @Builder.Default
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(mappedBy = "classEntity")
    @Builder.Default
    @JsonIgnore
    private List<Exam> exams = new ArrayList<>();

    @OneToMany(mappedBy = "classEntity")
    @Builder.Default
    @JsonIgnore
    private List<FinalExam> finalExams = new ArrayList<>();

    @OneToMany(mappedBy = "classEntity")
    @Builder.Default
    @JsonIgnore
    private List<TeacherSubjectClass> teacherAssignments = new ArrayList<>();
}