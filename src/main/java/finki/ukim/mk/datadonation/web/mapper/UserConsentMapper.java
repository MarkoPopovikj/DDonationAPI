package finki.ukim.mk.datadonation.web.mapper;

import finki.ukim.mk.datadonation.domain.models.UserConsent;
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
        return mapToResponse(this.userConsentService.getUserConsentById(id));
    }

    public UserConsentResponse giveConsent(UUID id){
        return mapToResponse(this.userConsentService.giveConsent(id));
    }

    private UserConsentResponse mapToResponse(UserConsent userConsent) {
        return new UserConsentResponse(userConsent.getHasConsented());
    }
}
