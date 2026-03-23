package finki.ukim.mk.datadonation.service.impl;

import finki.ukim.mk.datadonation.model.User;
import finki.ukim.mk.datadonation.repository.UserRepository;
import finki.ukim.mk.datadonation.request.UserRequest;
import finki.ukim.mk.datadonation.service.StorageService;
import finki.ukim.mk.datadonation.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static finki.ukim.mk.datadonation.util.AvatarUtil.generatePlaceholder;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StorageService storageService;
    private final Tika tika;

    public UserServiceImpl(UserRepository userRepository, StorageService storageService) {
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.tika = new Tika();
    }

    @Override
    public boolean checkAvailability(String username, String email) {
        if (username != null && email != null) {
            return this.userRepository.existsByUsernameOrEmail(username, email);
        } else if (username != null) {
            return this.userRepository.existsByUsername(username);
        } else if (email != null) {
            return this.userRepository.existsByEmail(email);
        }
        return false;
    }

    @Override
    public User getUserById(UUID id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User updateUser(UUID id, UserRequest userRequest) {
        User user = getUserById(id);

        if (userRequest.getDisplayName() != null) user.setDisplayName(userRequest.getDisplayName());
        if (userRequest.getBio() != null) user.setBio(userRequest.getBio());

        return this.userRepository.save(user);
    }

    @Override
    public User uploadAvatar(UUID id, MultipartFile file) {
        User user = getUserById(id);
        String currentUrl = user.getAvatarUrl();

        if (file != null && !file.isEmpty()) {
            try {
                String detectedMimeType = tika.detect(file.getInputStream());

                if (!detectedMimeType.startsWith("image/")) {
                    throw new IllegalArgumentException("File must be an image. Detected: " + detectedMimeType);
                }

                this.storageService.deleteFile(currentUrl);

                String newUrl = storageService.uploadFile(file, "avatars/", detectedMimeType);
                user.setAvatarUrl(newUrl);

            } catch (Exception e) {
                log.error("Failed to process avatar upload for user {}", id, e);
                throw new RuntimeException("Could not upload avatar: " + e.getMessage());
            }
        } else {
            if (currentUrl != null && currentUrl.contains("placehold.co")) {
                return user;
            }

            this.storageService.deleteFile(currentUrl);
            user.setAvatarUrl(generatePlaceholder(user.getUsername()));
        }

        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = getUserById(id);

        this.storageService.deleteFile(user.getAvatarUrl());
        this.userRepository.delete(user);
    }
}
