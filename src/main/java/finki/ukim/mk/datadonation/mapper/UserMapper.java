package finki.ukim.mk.datadonation.mapper;

import finki.ukim.mk.datadonation.model.User;
import finki.ukim.mk.datadonation.request.UserRequest;
import finki.ukim.mk.datadonation.response.UserResponse;
import finki.ukim.mk.datadonation.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class UserMapper {

    private final UserService userService;

    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public boolean checkAvailability(String username, String email) {
        return this.userService.checkAvailability(username, email);
    }

    public UserResponse getUserById(UUID id) {
        return mapToResponse(this.userService.getUserById(id));
    }

    public UserResponse getUserByUsername(String username) {
        return mapToResponse(this.userService.getUserByUsername(username));
    }

    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        return mapToResponse(this.userService.updateUser(id, userRequest));
    }

    public UserResponse uploadAvatar(UUID id, MultipartFile file) {
        return mapToResponse(this.userService.uploadAvatar(id, file));
    }

    public void deleteUser(UUID id) {
        this.userService.deleteUser(id);
    }

    public UserResponse mapToResponse(User user) {
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

    public Page<UserResponse> mapToPageableResponse(Page<User> items) {
        return items.map(this::mapToResponse);
    }
}
