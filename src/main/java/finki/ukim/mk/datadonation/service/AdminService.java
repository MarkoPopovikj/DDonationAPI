package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.model.User;
import finki.ukim.mk.datadonation.request.AdminRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    Page<User> getAdmins(Pageable pageable);

    User createAdminUser(AdminRequest adminRequest);

}
