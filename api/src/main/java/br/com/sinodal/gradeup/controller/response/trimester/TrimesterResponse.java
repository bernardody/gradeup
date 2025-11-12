package br.com.sinodal.gradeup.controller.response.trimester;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrimesterResponse {

    private Long id;
    private String name;
    private BigDecimal maxPoints;
    private BigDecimal minPoints;
}