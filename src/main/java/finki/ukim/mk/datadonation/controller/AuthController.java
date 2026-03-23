package finki.ukim.mk.datadonation.controller;

import finki.ukim.mk.datadonation.mapper.AuthMapper;
import finki.ukim.mk.datadonation.request.auth.LoginRequest;
import finki.ukim.mk.datadonation.request.auth.RefreshTokenRequest;
import finki.ukim.mk.datadonation.request.auth.RegisterRequest;
import finki.ukim.mk.datadonation.response.auth.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthMapper authMapper;

    public AuthController(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(this.authMapper.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(this.authMapper.login(loginRequest));
    }

    @PostMapping("/refresh-tokens")
    public ResponseEntity<AuthResponse> refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(this.authMapper.refreshToken(refreshTokenRequest));
    }
}
