package com.ecore.teamroles.service;

import com.ecore.teamroles.dto.MembershipDto;
import com.ecore.teamroles.model.Role;
import com.ecore.teamroles.model.TeamMemberRole;
import com.ecore.teamroles.repository.RoleRepository;
import com.ecore.teamroles.repository.TeamMemberRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TeamMemberRoleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TeamMemberRoleServiceImplTest {
    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private TeamMemberRoleRepository teamMemberRoleRepository;

    @Autowired
    private TeamMemberRoleServiceImpl teamMemberRoleServiceImpl;

    /**
     * Method under test: {@link TeamMemberRoleServiceImpl#addMemberRole(MembershipDto)}
     */
    @Test
    void testAddMemberRole() {
        when(teamMemberRoleRepository.save((TeamMemberRole) any())).thenReturn(new TeamMemberRole("42", "42"));

        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setId(123L);
        role.setName("Name");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        role.setVersion(1L);
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByName((String) any())).thenReturn(ofResult);
        teamMemberRoleServiceImpl.addMemberRole(new MembershipDto("42", "42", "Role Name"));
        verify(teamMemberRoleRepository).save((TeamMemberRole) any());
        verify(roleRepository).findByName((String) any());
    }

    /**
     * Method under test: {@link TeamMemberRoleServiceImpl#addMemberRole(MembershipDto)}
     */
    @Test
    void testAddMemberRole2() {
        when(teamMemberRoleRepository.save((TeamMemberRole) any()))
                .thenThrow(new ConstraintViolationException(new HashSet<>()));

        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setId(123L);
        role.setName("Name");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        role.setVersion(1L);
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByName((String) any())).thenReturn(ofResult);
        teamMemberRoleServiceImpl.addMemberRole(new MembershipDto("42", "42", "Role Name"));
        verify(teamMemberRoleRepository).save((TeamMemberRole) any());
        verify(roleRepository).findByName((String) any());
    }


}

