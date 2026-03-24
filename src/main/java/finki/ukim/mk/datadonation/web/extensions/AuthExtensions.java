package finki.ukim.mk.datadonation.web.extensions;

import finki.ukim.mk.datadonation.domain.dto.AuthDto;
import finki.ukim.mk.datadonation.web.response.auth.AuthResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AuthExtensions {

    public static AuthResponse toResponse(AuthDto authDto) {
        if (authDto == null) return null;

        return new AuthResponse(
                UserExtensions.toResponse(authDto.getUser()),
                authDto.getTokens()
        );
    }
}
