package finki.ukim.mk.datadonation.web.mapper;

import finki.ukim.mk.datadonation.domain.dto.AuthDto;
import finki.ukim.mk.datadonation.web.extensions.AuthExtensions;
import finki.ukim.mk.datadonation.web.request.auth.LoginRequest;
import finki.ukim.mk.datadonation.web.request.auth.RefreshTokenRequest;
import finki.ukim.mk.datadonation.web.request.auth.RegisterRequest;
import finki.ukim.mk.datadonation.web.response.auth.AuthResponse;
import finki.ukim.mk.datadonation.service.AuthService;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    private final AuthService authService;

    public AuthMapper(AuthService authService) {
        this.authService = authService;
    }

    public AuthResponse register(RegisterRequest request) {
        AuthDto authDto = this.authService.register(request);
        return AuthExtensions.toResponse(authDto);
    }

    public AuthResponse login(LoginRequest request) {
        AuthDto authDto = this.authService.login(request);
        return AuthExtensions.toResponse(authDto);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        AuthDto authDto = this.authService.refreshToken(request);
        return AuthExtensions.toResponse(authDto);
    }

}
