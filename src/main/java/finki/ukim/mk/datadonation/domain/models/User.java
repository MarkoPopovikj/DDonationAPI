package finki.ukim.mk.datadonation.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finki.ukim.mk.datadonation.domain.common.BaseAuditableEntity;
import finki.ukim.mk.datadonation.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseAuditableEntity {

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "bio")
    private String bio;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.DONOR;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserConsent userConsent;

    @JsonIgnore
    @OneToMany(mappedBy = "donor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Item> items;
}