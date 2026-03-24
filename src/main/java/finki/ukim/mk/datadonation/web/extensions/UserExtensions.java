package finki.ukim.mk.datadonation.web.extensions;

import finki.ukim.mk.datadonation.domain.dto.AdminDto;
import finki.ukim.mk.datadonation.domain.dto.ItemDto;
import finki.ukim.mk.datadonation.domain.dto.UserDto;
import finki.ukim.mk.datadonation.domain.models.User;
import finki.ukim.mk.datadonation.web.request.AdminRequest;
import finki.ukim.mk.datadonation.web.request.UserRequest;
import finki.ukim.mk.datadonation.web.response.UserResponse;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
public final class UserExtensions {

    public static UserResponse toResponse(User user) {
        if (user == null) return null;

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getDisplayName(),
                user.getCreatedAt(),
                user.getBio(),
                user.getRole()
        );
    }

    public static Page<UserResponse> toResponse(Page<User> users) {
        return users.map(UserExtensions::toResponse);
    }

    public static UserDto toDto(UserRequest userRequest) {
        if (userRequest == null) return null;

        return new UserDto(
                userRequest.displayName(),
                userRequest.bio()
        );
    }

    public static AdminDto toDto(AdminRequest adminRequest) {
        if (adminRequest == null) return null;

        return new AdminDto(
                adminRequest.email(),
                adminRequest.username(),
                adminRequest.password(),
                adminRequest.confirmPassword(),
                adminRequest.role()
        );
    }
}
