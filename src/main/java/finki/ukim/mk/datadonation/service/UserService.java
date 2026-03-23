package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.model.User;
import finki.ukim.mk.datadonation.request.AdminRequest;
import finki.ukim.mk.datadonation.request.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {

    boolean checkAvailability(String username, String email);

    User getUserById(UUID id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User updateUser(UUID id, UserRequest userRequest);

    User uploadAvatar(UUID id, MultipartFile file);

    void deleteUser(UUID id);

}
