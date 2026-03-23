package finki.ukim.mk.datadonation.mapper;

import finki.ukim.mk.datadonation.model.User;
import finki.ukim.mk.datadonation.request.AdminRequest;
import finki.ukim.mk.datadonation.response.UserResponse;
import finki.ukim.mk.datadonation.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    private final AdminService adminService;
    private final UserMapper userMapper;

    public AdminMapper(AdminService adminService, UserMapper userMapper) {
        this.adminService = adminService;
        this.userMapper = userMapper;
    }

    public Page<UserResponse> getAdmins(Pageable pageable) {
        return this.userMapper.mapToPageableResponse(this.adminService.getAdmins(pageable));
    }

    public UserResponse createAdminUser(AdminRequest adminRequest) {
        return this.userMapper.mapToResponse(this.adminService.createAdminUser(adminRequest));
    }
}
