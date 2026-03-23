package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.model.dto.AuthDto;
import finki.ukim.mk.datadonation.request.auth.LoginRequest;
import finki.ukim.mk.datadonation.request.auth.RefreshTokenRequest;
import finki.ukim.mk.datadonation.request.auth.RegisterRequest;

public interface AuthService {

    AuthDto register(RegisterRequest registerRequest);

    AuthDto login(LoginRequest loginRequest);

    AuthDto refreshToken(RefreshTokenRequest refreshTokenRequest);

}
