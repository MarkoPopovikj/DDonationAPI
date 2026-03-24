package finki.ukim.mk.datadonation.web.mapper;

import finki.ukim.mk.datadonation.domain.models.User;
import finki.ukim.mk.datadonation.web.extensions.UserExtensions;
import finki.ukim.mk.datadonation.web.request.UserRequest;
import finki.ukim.mk.datadonation.web.response.UserResponse;
import finki.ukim.mk.datadonation.service.UserService;
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
        User user = this.userService.getUserById(id);
        return UserExtensions.toResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = this.userService.getUserByUsername(username);
        return UserExtensions.toResponse(user);
    }

    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        User user = this.userService.updateUser(id, UserExtensions.toDto(userRequest));
        return UserExtensions.toResponse(user);
    }

    public UserResponse uploadAvatar(UUID id, MultipartFile file) {
        User user = this.userService.uploadAvatar(id, file);
        return UserExtensions.toResponse(user);
    }

    public void deleteUser(UUID id) {
        this.userService.deleteUser(id);
    }

}
