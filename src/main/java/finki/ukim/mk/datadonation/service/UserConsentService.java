package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.model.UserConsent;

import java.util.UUID;

public interface UserConsentService {

    UserConsent getUserConsentById(UUID id);

    UserConsent giveConsent(UUID userId);

    void createUserConsent(UUID userId);

}
