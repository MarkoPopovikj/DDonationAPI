package finki.ukim.mk.datadonation.domain.dto;

import finki.ukim.mk.datadonation.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDto {

    private User user;

    private TokenWrapperDto tokens;

}
