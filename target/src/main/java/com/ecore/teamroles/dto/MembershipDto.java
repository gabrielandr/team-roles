package com.ecore.teamroles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipDto {

    @NotBlank(message = "User Id cannot be empty")
    private String userId;
    @NotBlank(message = "Team Id cannot be empty")
    private String teamId;
    @NotBlank(message = "Role name cannot be empty")
    private String roleName;
}
