package finki.ukim.mk.datadonation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TokenDto {

    String token;

    Date expires;

}
