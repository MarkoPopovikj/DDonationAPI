package finki.ukim.mk.datadonation.web.request;

import finki.ukim.mk.datadonation.domain.enums.UserRole;

public record AdminRequest(

        String email,

        String username,

        String password,

        String confirmPassword,

        UserRole role

) {
}
