package finki.ukim.mk.datadonation.response.auth;

import finki.ukim.mk.datadonation.model.dto.TokenWrapperDto;
import finki.ukim.mk.datadonation.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private UserResponse user;

    private TokenWrapperDto tokens;

}
