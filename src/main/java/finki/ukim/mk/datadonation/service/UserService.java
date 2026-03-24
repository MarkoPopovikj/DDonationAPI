package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.domain.dto.UserDto;
import finki.ukim.mk.datadonation.domain.models.User;
import finki.ukim.mk.datadonation.web.request.UserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {

    boolean checkAvailability(String username, String email);

    User getUserById(UUID id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User updateUser(UUID id, UserDto userDto);

    User uploadAvatar(UUID id, MultipartFile file);

    void deleteUser(UUID id);

}
