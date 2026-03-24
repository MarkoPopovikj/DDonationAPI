package finki.ukim.mk.datadonation.repository;

import finki.ukim.mk.datadonation.domain.models.UserConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserConsentRepository extends JpaRepository<UserConsent, UUID> {
}
