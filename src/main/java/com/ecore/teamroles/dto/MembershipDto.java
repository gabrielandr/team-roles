package com.ecore.teamroles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipDto {

    @NotNull
    private UserDTO user;
    @NotNull
    private TeamDTO team;
    private String roleName;
}
