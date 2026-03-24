package finki.ukim.mk.datadonation.web.controller;

import finki.ukim.mk.datadonation.web.mapper.UserConsentMapper;
import finki.ukim.mk.datadonation.web.response.UserConsentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/user-consent")
public class UserConsentController {

    private final UserConsentMapper userConsentMapper;

    public UserConsentController(UserConsentMapper userConsentMapper) {
        this.userConsentMapper = userConsentMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<UserConsentResponse> getUserConsent(Principal principal) {
        return ResponseEntity.ok(this.userConsentMapper.getUserConsent(UUID.fromString(principal.getName())));
    }

    @PutMapping("/me")
    public ResponseEntity<UserConsentResponse> giveUserConsent(Principal principal) {
        return ResponseEntity.ok(this.userConsentMapper.giveConsent(UUID.fromString(principal.getName())));
    }

}
