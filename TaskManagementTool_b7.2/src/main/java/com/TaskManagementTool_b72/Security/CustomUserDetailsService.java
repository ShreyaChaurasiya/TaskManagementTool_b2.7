package com.TaskManagementTool_b72.Security;

import com.TaskManagementTool_b72.Entity.UserAuth;
import com.TaskManagementTool_b72.Repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAuthRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userOfficialEmail) throws UsernameNotFoundException {

        UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserOfficialEmail(),
                user.getPassword(),
                Collections.emptyList() // no roles for now
        );
    }
}