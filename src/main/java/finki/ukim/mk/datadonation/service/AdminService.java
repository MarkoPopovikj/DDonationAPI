package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.domain.dto.AdminDto;
import finki.ukim.mk.datadonation.domain.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    Page<User> getAdmins(Pageable pageable);

    User createAdminUser(AdminDto adminDto);

}
