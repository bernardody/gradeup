package br.com.sinodal.gradeup.controller.response.subject;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectResponse {

    private Long id;
    private String name;
}