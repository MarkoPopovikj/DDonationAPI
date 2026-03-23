package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.model.User;
import finki.ukim.mk.datadonation.model.dto.TokenWrapperDto;
import finki.ukim.mk.datadonation.model.enums.TokenType;
import io.jsonwebtoken.Claims;

public interface TokenService {

    TokenWrapperDto generateAuthTokens(User user);

    Claims verifyToken(String token, TokenType expectedType);

    String extractUserIdFromToken(String token, TokenType type);

}
