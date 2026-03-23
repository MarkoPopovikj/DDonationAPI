package finki.ukim.mk.datadonation.service.impl;

import finki.ukim.mk.datadonation.model.User;
import finki.ukim.mk.datadonation.model.dto.AuthDto;
import finki.ukim.mk.datadonation.model.dto.TokenWrapperDto;
import finki.ukim.mk.datadonation.model.enums.TokenType;
import finki.ukim.mk.datadonation.repository.UserRepository;
import finki.ukim.mk.datadonation.request.auth.LoginRequest;
import finki.ukim.mk.datadonation.request.auth.RefreshTokenRequest;
import finki.ukim.mk.datadonation.request.auth.RegisterRequest;
import finki.ukim.mk.datadonation.service.AuthService;
import finki.ukim.mk.datadonation.service.TokenService;
import finki.ukim.mk.datadonation.service.UserConsentService;
import finki.ukim.mk.datadonation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static finki.ukim.mk.datadonation.util.AvatarUtil.generatePlaceholder;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserConsentService userConsentService;

    public AuthServiceImpl(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder, UserService userService, UserConsentService userConsentService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userConsentService = userConsentService;
    }

    @Override
    public AuthDto register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userService.checkAvailability(registerRequest.getUsername(), registerRequest.getEmail())) {
            throw new IllegalArgumentException("Username or Email is already taken");
        }

        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAvatarUrl(generatePlaceholder(registerRequest.getUsername()));

        User savedUser = this.userRepository.save(user);

        this.userConsentService.createUserConsent(savedUser.getId());
        return buildAuthDto(savedUser);
    }

    @Override
    public AuthDto login(LoginRequest loginRequest) {
        User user = userService.getUserByEmail(loginRequest.getEmail());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect email or password");
        }

        return buildAuthDto(user);
    }

    @Override
    public AuthDto refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String userIdStr = tokenService.extractUserIdFromToken(refreshTokenRequest.getRefreshToken(), TokenType.REFRESH);
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.getUserById(userId);

            return buildAuthDto(user);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid or expired refresh token. Please log in again.");
        }
    }

    private AuthDto buildAuthDto(User user) {
        TokenWrapperDto tokens = tokenService.generateAuthTokens(user);
        return new AuthDto(user, tokens);
    }
}
