package finki.ukim.mk.datadonation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TokenDto {

    String token;

    Date expires;

}
