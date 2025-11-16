package br.com.sinodal.gradeup.domain;

import br.com.sinodal.gradeup.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(mappedBy = "student")
    @Builder.Default
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    @Builder.Default
    @JsonIgnore
    private List<Grade> grades = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    @Builder.Default
    @JsonIgnore
    private List<FinalGrade> finalGrades = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    @Builder.Default
    @JsonIgnore
    private List<Exam> examsCreated = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    @Builder.Default
    @JsonIgnore
    private List<FinalExam> finalExamsCreated = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    @Builder.Default
    @JsonIgnore
    private List<TeacherSubjectClass> teacherAssignments = new ArrayList<>();
}