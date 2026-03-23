package finki.ukim.mk.datadonation.request;

import finki.ukim.mk.datadonation.model.enums.UserRole;
import lombok.Getter;

@Getter
public class AdminRequest {

    private String email;

    private String username;

    private String password;

    private String confirmPassword;

    private UserRole role;

}
