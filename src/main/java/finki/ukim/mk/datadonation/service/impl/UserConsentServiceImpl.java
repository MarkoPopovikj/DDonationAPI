package finki.ukim.mk.datadonation.service.impl;

import finki.ukim.mk.datadonation.domain.models.User;
import finki.ukim.mk.datadonation.domain.models.UserConsent;
import finki.ukim.mk.datadonation.repository.UserConsentRepository;
import finki.ukim.mk.datadonation.service.UserConsentService;
import finki.ukim.mk.datadonation.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserConsentServiceImpl implements UserConsentService {

    private final UserConsentRepository userConsentRepository;
    private final UserService userService;

    public UserConsentServiceImpl(UserConsentRepository userConsentRepository, UserService userService) {
        this.userConsentRepository = userConsentRepository;
        this.userService = userService;
    }

    @Override
    public UserConsent getUserConsentById(UUID id) {
        return this.userConsentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserConsent with id " + id + " not found"));
    }

    @Override
    public UserConsent giveConsent(UUID userId) {
        UserConsent userConsent = getUserConsentById(userId);

        userConsent.setHasConsented(true);
        return this.userConsentRepository.save(userConsent);
    }

    @Override
    public void createUserConsent(UUID userId) {
        User user = userService.getUserById(userId);

        UserConsent userConsent = new UserConsent();
        userConsent.setUser(user);

        this.userConsentRepository.save(userConsent);
    }
}
