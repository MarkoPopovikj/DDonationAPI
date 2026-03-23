package finki.ukim.mk.datadonation.controller;

import finki.ukim.mk.datadonation.mapper.UserMapper;
import finki.ukim.mk.datadonation.request.AdminRequest;
import finki.ukim.mk.datadonation.request.UserRequest;
import finki.ukim.mk.datadonation.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/available")
    public ResponseEntity<Void> checkAvailability(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    ) {
        boolean isTaken = userMapper.checkAvailability(username, email);
        if (isTaken) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserById(Principal principal) {
        return ResponseEntity.ok(this.userMapper.getUserById(UUID.fromString(principal.getName())));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(this.userMapper.getUserByUsername(username));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody UserRequest userRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(this.userMapper.updateUser(UUID.fromString(principal.getName()), userRequest));
    }

    @PutMapping(value = "/me/avatar")
    public ResponseEntity<UserResponse> uploadAvatar(
            @RequestParam(value = "image", required = false) MultipartFile file,
            Principal principal
    ) {

        return ResponseEntity.ok(this.userMapper.uploadAvatar(UUID.fromString(principal.getName()), file));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        this.userMapper.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
