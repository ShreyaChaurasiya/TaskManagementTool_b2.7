package com.TaskManagementTool_b72.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.TaskManagementTool_b72.DTO.AuthResponseDTO;
import com.TaskManagementTool_b72.DTO.LoginRequestDTO;
import com.TaskManagementTool_b72.DTO.RegisterRequestDTO;
import com.TaskManagementTool_b72.Entity.UserAuth;
import com.TaskManagementTool_b72.Repository.UserAuthRepository;
import com.TaskManagementTool_b72.Security.JWTUtil;
import com.TaskManagementTool_b72.Security.TokenBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import jakarta.servlet.http.HttpServletRequest;

@Service

public class UserAuthService {
    @Autowired
    private UserAuthRepository userRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailLogService emailService;

    @Autowired
    private TokenBlockService tokenBlock;

    public AuthResponseDTO register(RegisterRequestDTO register) {


        Optional<UserAuth>exist=userRepo.findByUserOfficialEmail(register.userOfficialEmail);
        if(exist.isPresent()) {
            throw new RuntimeException("User already exist");
        }

        UserAuth user= new UserAuth();

        user.setUserName(register.userName);
        user.setUserOfficialEmail(register.userOfficialEmail);
        user.setPassword(passwordEncoder.encode(register.password));
        user.setRole(register.role);

        userRepo.save(user);

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDTO(token,"user registered successfully");
    }


    public String login(LoginRequestDTO login) {

        UserAuth user = userRepo.findByUserOfficialEmail(login.userOfficialEmail)
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(login.password, user.getPassword())) {
            throw new RuntimeException("invalid credential");
        }

        return jwtUtil.generateToken(user);

    }


    public void forgotPassword(String email) {

        UserAuth user= userRepo.findByUserOfficialEmail(email).orElseThrow(()-> new RuntimeException("user not found"));

        String token= UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpire(new Date(System.currentTimeMillis()+10*60*1000));

        userRepo.save(user);

        emailService.sentResetPasswordEmail(email, token);

    }


    public void resetPassword(String token, String newPassword) {

        UserAuth user= userRepo.findByResetToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));

        if(user.getResetTokenExpire().before(new Date())) {

            throw new RuntimeException("Token got expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword) );
        user.setResetToken(null);
        user.setResetTokenExpire(null);

        userRepo.save(user);

    }

    public String logout(HttpServletRequest request) {
        String header= request.getHeader("Authorization");
        String token= jwtUtil.extractToken(header);

        if(token!=null) {
            tokenBlock.blockListToken(token);
        }

        return "Logged Out Successfully ";
    }
}
