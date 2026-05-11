package com.TaskManagementTool_b72.Controller;

import com.TaskManagementTool_b72.DTO.AuthResponseDTO;
import com.TaskManagementTool_b72.DTO.LoginRequestDTO;
import com.TaskManagementTool_b72.DTO.RegisterRequestDTO;
import com.TaskManagementTool_b72.Service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user_auth")
@RequiredArgsConstructor
public class UserAuthController {
    @Autowired
    private UserAuthService userService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO register){
        return ResponseEntity.ok(userService.register(register));
    }

    @PostMapping("/login")
    public ResponseEntity<String>login(@RequestBody LoginRequestDTO login){
        String token = userService.login(login);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<String>forgotPasswod(@RequestParam String email){
        userService.forgotPassword(email);
        return ResponseEntity.ok("Reset password-Email sent overyour email");
    }

    @PostMapping("/reset_password/{token}/{newPassword}")
    public ResponseEntity<String>resetPassword(@PathVariable String token,@PathVariable String newPassword){
        userService.resetPassword(token,newPassword );
        return ResponseEntity.ok("Password reset successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String>logout(HttpServletRequest request){
        return ResponseEntity.ok(userService.logout(request));
    }

}
