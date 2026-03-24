package finki.ukim.mk.datadonation.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public record UserConsentResponse(
        Boolean hasConsented
) {
}
