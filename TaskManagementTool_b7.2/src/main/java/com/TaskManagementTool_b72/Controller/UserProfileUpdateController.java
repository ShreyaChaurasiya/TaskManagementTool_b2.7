package com.TaskManagementTool_b72.Controller;

import java.util.List;

import com.TaskManagementTool_b72.Service.UserProfileUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagementTool_b72.DTO.UserProfileUpdateDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user_profile_update")
@RequiredArgsConstructor
public class UserProfileUpdateController {



    @Autowired
    private UserProfileUpdateService userProfileUpdateService;



    @PutMapping("/user_profile/update")
    public ResponseEntity<UserProfileUpdateDTO>updateUserProfile(@RequestBody UserProfileUpdateDTO userProfileUpdate){
        return ResponseEntity.ok(userProfileUpdateService.updateUserProfile(userProfileUpdate));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserProfileUpdateDTO>getUserProfileByEmail(@PathVariable String userEmail){
        return ResponseEntity.ok(userProfileUpdateService.getProfileByEmail(userEmail));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserProfileUpdateDTO>>getAllUserProfile(){
        return ResponseEntity.ok(userProfileUpdateService.getAllProfile());
    }
}

