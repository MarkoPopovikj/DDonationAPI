package finki.ukim.mk.datadonation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenWrapperDto {

    private TokenDto access;

    private TokenDto refresh;

}
