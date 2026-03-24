package finki.ukim.mk.datadonation.domain.dto;

import finki.ukim.mk.datadonation.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDto {

    private String email;

    private String username;

    private String password;

    private String confirmPassword;

    private UserRole role;

}
