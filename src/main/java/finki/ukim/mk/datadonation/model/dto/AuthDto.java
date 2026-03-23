package finki.ukim.mk.datadonation.model.dto;

import finki.ukim.mk.datadonation.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDto {

    private User user;

    private TokenWrapperDto tokens;

}
