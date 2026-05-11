package com.TaskManagementTool_b72.DTO;

import com.TaskManagementTool_b72.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class RegisterRequestDTO {

    public String userName;
    public String userOfficialEmail;
    public String password;
    public Role role;


    }
