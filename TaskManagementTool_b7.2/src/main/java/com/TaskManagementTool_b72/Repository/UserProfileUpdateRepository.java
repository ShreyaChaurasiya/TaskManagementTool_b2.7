package com.TaskManagementTool_b72.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool_b72.Entity.UserProfileUpdate;

@Repository
public interface UserProfileUpdateRepository extends JpaRepository<UserProfileUpdate,Long> {

    Optional<UserProfileUpdate>findByUserEmail(String userEmail);

}

