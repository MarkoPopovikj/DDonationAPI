package finki.ukim.mk.datadonation.web.extensions;

import finki.ukim.mk.datadonation.domain.models.UserConsent;
import finki.ukim.mk.datadonation.web.response.UserConsentResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class UserConsentExtensions {

    public static UserConsentResponse toResponse(UserConsent userConsent) {
        if (userConsent == null) return null;

        return new UserConsentResponse(
                userConsent.getHasConsented()
        );
    }
}
