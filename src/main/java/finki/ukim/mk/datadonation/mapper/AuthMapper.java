package finki.ukim.mk.datadonation.mapper;

import finki.ukim.mk.datadonation.model.dto.AuthDto;
import finki.ukim.mk.datadonation.request.auth.LoginRequest;
import finki.ukim.mk.datadonation.request.auth.RefreshTokenRequest;
import finki.ukim.mk.datadonation.request.auth.RegisterRequest;
import finki.ukim.mk.datadonation.response.auth.AuthResponse;
import finki.ukim.mk.datadonation.service.AuthService;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    private final AuthService authService;
    private final UserMapper userMapper;

    public AuthMapper(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    public AuthResponse register(RegisterRequest request) {
        return mapToResponse(this.authService.register(request));
    }

    public AuthResponse login(LoginRequest request) {
        return mapToResponse(this.authService.login(request));
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        return mapToResponse(this.authService.refreshToken(request));
    }

    public AuthResponse mapToResponse(AuthDto authDto) {
        return new AuthResponse(
                this.userMapper.mapToResponse(authDto.getUser()),
                authDto.getTokens()
        );
    }
}
