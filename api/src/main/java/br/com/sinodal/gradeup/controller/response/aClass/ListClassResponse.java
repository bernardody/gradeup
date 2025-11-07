package br.com.sinodal.gradeup.controller.response.aClass;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListClassResponse {

    private Long id;
    private String name;
    private int year;
}
