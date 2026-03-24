package finki.ukim.mk.datadonation.web.response.auth;

import finki.ukim.mk.datadonation.domain.dto.TokenWrapperDto;
import finki.ukim.mk.datadonation.web.response.UserResponse;

public record AuthResponse(
        UserResponse user,
        TokenWrapperDto tokens
) {}