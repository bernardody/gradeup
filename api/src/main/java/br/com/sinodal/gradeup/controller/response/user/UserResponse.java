package br.com.sinodal.gradeup.controller.response.user;

import br.com.sinodal.gradeup.enums.UserType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private UserType type;
}
