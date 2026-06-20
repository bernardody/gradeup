package br.com.sinodal.gradeup.controller.response.warning;

import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WarningResponse {

    private Long id;
    private ExamResponse examEntity;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
