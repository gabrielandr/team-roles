package com.ecore.teamroles.service;

import com.ecore.teamroles.dto.MembershipDto;
import com.ecore.teamroles.dto.TeamDTO;
import com.ecore.teamroles.dto.UserDTO;
import com.ecore.teamroles.model.Role;
import com.ecore.teamroles.model.TeamMemberRole;
import com.ecore.teamroles.repository.RoleRepository;
import com.ecore.teamroles.repository.TeamMemberRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
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
            TeamMemberRole teamMemberRole = new TeamMemberRole(membershipDto.getUser().getId(), membershipDto.getTeam().getId());
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
    public List<MembershipDto> findMemberships(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        List<MembershipDto> membershipDtos = new ArrayList<>();
        List<TeamMemberRole> memberships = null;
        if (role.isPresent()) {
            memberships = teamMemberRoleRepository.findByRole(role.get());
        }
        if (memberships.size() > 0) {
            for (TeamMemberRole membership : memberships) {
                MembershipDto membershipDto = new MembershipDto();
                UserDTO userInfo = getUserInfo(membership.getUserId());
                TeamDTO teamInfo = getTeamInfo(membership.getTeamId());
                if (userInfo != null) {
                    membershipDto.setUser(userInfo);
                }
                if (teamInfo != null) {
                    membershipDto.setTeam(teamInfo);
                }
                membershipDto.setRoleName(roleName);
                membershipDtos.add(membershipDto);
            }

        }
        return membershipDtos;
    }

    /**
     * Get user info from the api
     *
     * @param userId
     * @return
     */
    private UserDTO getUserInfo(String userId) {
        WebClient webClient = WebClient.create("https://cgjresszgg.execute-api.eu-west-1.amazonaws.com");
        return webClient.get().uri("/users/{userId}", userId).retrieve().bodyToMono(UserDTO.class).block();
    }

    /**
     * Get team info from the api
     *
     * @param teamId
     * @return
     */
    private TeamDTO getTeamInfo(String teamId) {
        WebClient webClient = WebClient.create("https://cgjresszgg.execute-api.eu-west-1.amazonaws.com");
        return webClient.get().uri("/teams/{teamId}", teamId).retrieve().bodyToMono(TeamDTO.class).block();
    }
}
