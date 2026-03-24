package finki.ukim.mk.datadonation.service.impl;

import finki.ukim.mk.datadonation.domain.models.User;
import finki.ukim.mk.datadonation.domain.dto.TokenDto;
import finki.ukim.mk.datadonation.domain.dto.TokenWrapperDto;
import finki.ukim.mk.datadonation.domain.enums.TokenType;
import finki.ukim.mk.datadonation.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
public class JwtTokenServiceImpl implements TokenService {

    @Value("${application.security.jwt.secret}")
    private String accessSecret;

    @Value("${application.security.jwt.token.secret}")
    private String refreshSecret;

    @Value("${application.security.jwt.access.expiration.minutes}")
    private long accessExpirationMinutes;

    @Value("${application.security.jwt.refresh.expiration.days}")
    private long refreshExpirationDays;

    @Override
    public TokenWrapperDto generateAuthTokens(User user) {
        long nowMillis = System.currentTimeMillis();

        Date accessExpires = new Date(nowMillis + (accessExpirationMinutes * 60 * 1000));
        Date refreshExpires = new Date(nowMillis + (refreshExpirationDays * 24 * 60 * 60 * 1000));

        String accessToken = buildToken(user, accessExpires, TokenType.ACCESS, getAccessKey());
        String refreshToken = buildToken(user, refreshExpires, TokenType.REFRESH, getRefreshKey());

        return new TokenWrapperDto(
                new TokenDto(accessToken, accessExpires),
                new TokenDto(refreshToken, refreshExpires)
        );
    }

    @Override
    public Claims verifyToken(String token, TokenType expectedType) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token is required");
        }

        try {
            SecretKey key = expectedType == TokenType.ACCESS ? getAccessKey() : getRefreshKey();

            Claims payload = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String tokenTypeString = payload.get("type", String.class);
            if (!expectedType.name().equals(tokenTypeString)) {
                throw new IllegalArgumentException("Token not valid for this action");
            }

            return payload;

        } catch (Exception e) {
            throw new IllegalArgumentException("Token not valid: " + e.getMessage());
        }
    }

    @Override
    public String extractUserIdFromToken(String token, TokenType type) {
        return verifyToken(token, type).getSubject();
    }

    private String buildToken(User user, Date expiration, TokenType type, SecretKey signingKey) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("type", type.name())
                .claim("role", user.getRole().name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    private SecretKey getAccessKey() {
        byte[] keyBytes = Decoders.BASE64.decode(accessSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getRefreshKey() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
