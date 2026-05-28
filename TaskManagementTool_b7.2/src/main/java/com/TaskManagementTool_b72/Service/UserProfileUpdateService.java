package com.TaskManagementTool_b72.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagementTool_b72.DTO.UserProfileUpdateDTO;
import com.TaskManagementTool_b72.Entity.UserProfileUpdate;
import com.TaskManagementTool_b72.Repository.UserProfileUpdateRepository;

@Service
public class UserProfileUpdateService {

    @Autowired
    private UserProfileUpdateRepository userProfileUpdateRepo;

    public UserProfileUpdateDTO updateUserProfile(UserProfileUpdateDTO userProfile) {

        if(userProfileUpdateRepo.findByUserEmail(userProfile.userEmail).isPresent()){
            throw new RuntimeException("Profile already exists :"+userProfile.userEmail );
        }

        UserProfileUpdate profile= new UserProfileUpdate();
        profile.setUserName(userProfile.userName);
        profile.setUserEmail(userProfile.userEmail);
        profile.setDesignation(userProfile.designation);
        profile.setOrganizationName(userProfile.organizationName);
        profile.setActive(true);

        userProfileUpdateRepo.save(profile);
        return toDto(profile);

    }

    public UserProfileUpdateDTO getProfileByEmail(String userEmail){
        UserProfileUpdate user= userProfileUpdateRepo.findByUserEmail(userEmail).orElseThrow(()-> new RuntimeException("profile not found"));
        return toDto(user);
    }

    public List<UserProfileUpdateDTO>getAllProfile(){
        return userProfileUpdateRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private UserProfileUpdateDTO toDto(UserProfileUpdate user) {

        UserProfileUpdateDTO dto= new UserProfileUpdateDTO();

        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setDepartment(user.getDepartment());
        dto.setDesignation(user.getDesignation());
        dto.setOrganizationName(user.getOrganizationName());
        dto.setActive(user.isActive());

        return dto;
    }
}

