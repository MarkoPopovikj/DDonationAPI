package finki.ukim.mk.datadonation.web.mapper;

import finki.ukim.mk.datadonation.domain.models.UserConsent;
import finki.ukim.mk.datadonation.web.extensions.UserConsentExtensions;
import finki.ukim.mk.datadonation.web.response.UserConsentResponse;
import finki.ukim.mk.datadonation.service.UserConsentService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserConsentMapper {

    private final UserConsentService userConsentService;

    public UserConsentMapper(UserConsentService userConsentService) {
        this.userConsentService = userConsentService;
    }

    public UserConsentResponse getUserConsent(UUID id) {
        UserConsent userConsent = this.userConsentService.getUserConsentById(id);
        return UserConsentExtensions.toResponse(userConsent);
    }

    public UserConsentResponse giveConsent(UUID id){
        UserConsent userConsent = this.userConsentService.giveConsent(id);
        return UserConsentExtensions.toResponse(userConsent);
    }

}
