package br.com.sinodal.gradeup.controller.response.subject;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListSubjectResponse {

    private Long id;
    private String name;
}