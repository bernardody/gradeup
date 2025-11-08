package br.com.sinodal.gradeup.controller.response.classes;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassResponse {

    private Long id;
    private String name;
    private int year;
}
