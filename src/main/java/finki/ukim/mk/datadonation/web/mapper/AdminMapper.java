package finki.ukim.mk.datadonation.web.mapper;

import finki.ukim.mk.datadonation.domain.models.User;
import finki.ukim.mk.datadonation.web.extensions.UserExtensions;
import finki.ukim.mk.datadonation.web.request.AdminRequest;
import finki.ukim.mk.datadonation.web.response.UserResponse;
import finki.ukim.mk.datadonation.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    private final AdminService adminService;

    public AdminMapper(AdminService adminService) {
        this.adminService = adminService;
    }

    public Page<UserResponse> getAdmins(Pageable pageable) {
        Page<User> users = this.adminService.getAdmins(pageable);
        return UserExtensions.toResponse(users);
    }

    public UserResponse createAdminUser(AdminRequest adminRequest) {
        User user = this.adminService.createAdminUser(UserExtensions.toDto(adminRequest));
        return UserExtensions.toResponse(user);
    }
}
