package finki.ukim.mk.datadonation.controller;

import finki.ukim.mk.datadonation.mapper.AdminMapper;
import finki.ukim.mk.datadonation.request.AdminRequest;
import finki.ukim.mk.datadonation.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminMapper adminMapper;

    public AdminController(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllAdmins(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(this.adminMapper.getAdmins(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    public ResponseEntity<UserResponse> createAdminUser(@RequestBody AdminRequest adminRequest) {
        return ResponseEntity.ok(this.adminMapper.createAdminUser(adminRequest));
    }
}
