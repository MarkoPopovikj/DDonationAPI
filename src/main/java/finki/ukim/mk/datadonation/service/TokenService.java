package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.domain.models.User;
import finki.ukim.mk.datadonation.domain.dto.TokenWrapperDto;
import finki.ukim.mk.datadonation.domain.enums.TokenType;
import io.jsonwebtoken.Claims;

public interface TokenService {

    TokenWrapperDto generateAuthTokens(User user);

    Claims verifyToken(String token, TokenType expectedType);

    String extractUserIdFromToken(String token, TokenType type);

}
