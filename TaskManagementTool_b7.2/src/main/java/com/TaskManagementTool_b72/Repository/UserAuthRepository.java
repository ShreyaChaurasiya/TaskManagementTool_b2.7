package com.TaskManagementTool_b72.Repository;

import java.util.Optional;

import com.TaskManagementTool_b72.Entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {

    Optional<UserAuth>findByUserOfficialEmail(String userOfficialEmail);
    Optional<UserAuth>findByResetToken(String resetToken);
}
