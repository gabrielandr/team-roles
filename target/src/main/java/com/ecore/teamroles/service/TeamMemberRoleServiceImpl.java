package com.ecore.teamroles.service;

import com.ecore.teamroles.dto.MembershipDto;
import com.ecore.teamroles.model.Role;
import com.ecore.teamroles.model.TeamMemberRole;
import com.ecore.teamroles.repository.RoleRepository;
import com.ecore.teamroles.repository.TeamMemberRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeamMemberRoleServiceImpl implements TeamMemberRoleService {

    private final TeamMemberRoleRepository teamMemberRoleRepository;
    private final RoleRepository roleRepository;

    public TeamMemberRoleServiceImpl(TeamMemberRoleRepository teamMemberRoleRepository, RoleRepository roleRepository) {
        this.teamMemberRoleRepository = teamMemberRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public TeamMemberRole addMemberRole(MembershipDto membershipDto) {
        try {
            Optional<Role> role = roleRepository.findByName(membershipDto.getRoleName());
            TeamMemberRole teamMemberRole = new TeamMemberRole(membershipDto.getUserId(), membershipDto.getTeamId());
            if (role.isPresent()) {
                teamMemberRole.setRole(role.get());
            } else {
                Optional<Role> developerRole = roleRepository.findByName("Developer");
                teamMemberRole.setRole(developerRole.get());
            }
            return teamMemberRoleRepository.save(teamMemberRole);
        } catch (ConstraintViolationException e) {
            log.error("Erro when adding role to membership.");
        }
        return null;
    }

    @Override
    public List<TeamMemberRole> findMemberships(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        List<TeamMemberRole> memberships = null;
        if (role.isPresent()) {
            memberships = teamMemberRoleRepository.findByRole(role.get());
        }
        return memberships;
    }
}
