package finki.ukim.mk.datadonation.request.auth;

import lombok.Getter;

@Getter
public class RegisterRequest {

    private String email;

    private String username;

    private String password;

    private String confirmPassword;

}
