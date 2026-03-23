package finki.ukim.mk.datadonation.model;

import finki.ukim.mk.datadonation.model.common.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_consents")
public class UserConsent extends BaseAuditableEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "has_consented")
    private Boolean hasConsented = false;

}
