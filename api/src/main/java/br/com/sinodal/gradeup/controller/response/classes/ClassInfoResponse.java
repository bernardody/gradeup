package br.com.sinodal.gradeup.controller.response.classes;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassInfoResponse {
    private Long id;
    private String name;
    private Integer year;
}
