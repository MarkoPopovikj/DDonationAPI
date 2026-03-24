package finki.ukim.mk.datadonation.web.response;

import finki.ukim.mk.datadonation.domain.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,

        String username,

        String email,

        String avatarUrl,

        String displayName,

        LocalDateTime createdAt,

        String bio,

        UserRole role
) {
}