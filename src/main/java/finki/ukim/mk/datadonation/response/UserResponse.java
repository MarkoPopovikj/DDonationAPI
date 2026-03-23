package finki.ukim.mk.datadonation.response;

import finki.ukim.mk.datadonation.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponse {

    private UUID id;

    private String username;

    private String email;

    private String avatarUrl;

    private String displayName;

    private LocalDateTime createdAt;

    private String bio;

    private UserRole role;

}
