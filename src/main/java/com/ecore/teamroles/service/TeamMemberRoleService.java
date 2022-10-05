package com.ecore.teamroles.service;

import com.ecore.teamroles.dto.MembershipDto;
import com.ecore.teamroles.model.TeamMemberRole;

import java.util.List;

public interface TeamMemberRoleService {


    TeamMemberRole addMemberRole(MembershipDto teamMemberRole);

    List<MembershipDto> findMemberships(String roleName);
}
