package finki.ukim.mk.datadonation.service.impl;

import finki.ukim.mk.datadonation.domain.dto.AdminDto;
import finki.ukim.mk.datadonation.domain.models.User;
import finki.ukim.mk.datadonation.domain.enums.UserRole;
import finki.ukim.mk.datadonation.repository.UserRepository;
import finki.ukim.mk.datadonation.web.request.AdminRequest;
import finki.ukim.mk.datadonation.service.AdminService;
import finki.ukim.mk.datadonation.service.UserConsentService;
import finki.ukim.mk.datadonation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static finki.ukim.mk.datadonation.util.AvatarUtil.generatePlaceholder;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserConsentService userConsentService;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository, UserService userService, UserConsentService userConsentService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userConsentService = userConsentService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<User> getAdmins(Pageable pageable) {
        return this.userRepository.findAllByRoleNotIn(Collections.singleton(UserRole.DONOR), pageable);
    }

    @Override
    @Transactional
    public User createAdminUser(AdminDto adminDto) {
        if (!adminDto.getPassword().equals(adminDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (this.userService.checkAvailability(adminDto.getUsername(), adminDto.getEmail())) {
            throw new IllegalArgumentException("Username or Email is already taken");
        }

        User user = new User();

        user.setEmail(adminDto.getEmail());
        user.setUsername(adminDto.getUsername());
        user.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        user.setAvatarUrl(generatePlaceholder(adminDto.getUsername()));
        user.setRole(adminDto.getRole());

        User savedUser = this.userRepository.save(user);
        this.userConsentService.createUserConsent(savedUser.getId());

        return savedUser;
    }

}
