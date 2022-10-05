package com.ecore.teamroles.service;

import com.ecore.teamroles.dto.MembershipDto;
import com.ecore.teamroles.dto.TeamDTO;
import com.ecore.teamroles.dto.UserDTO;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TeamMemberRoleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TeamMemberRoleServiceImplTest {
    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private TeamMemberRoleRepository teamMemberRoleRepository;

    @Autowired
    private TeamMemberRoleServiceImpl teamMemberRoleServiceImpl;

    @Test
    void testAddMemberRole() {
        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setId(123L);
        role.setName("Tester");
        Optional<Role> optionalRole = Optional.of(role);

        when(roleRepository.findByName(any())).thenReturn(optionalRole);
        TeamMemberRole teamMemberRole = new TeamMemberRole("22", "22");
        teamMemberRole.setRole(optionalRole.get());
        when(teamMemberRoleRepository.save(any())).thenReturn(teamMemberRole);
        when(teamMemberRoleRepository.findByRole(any())).thenReturn(Arrays.asList(teamMemberRole));

        List<TeamMemberRole> teamMemberRoleList = teamMemberRoleRepository.findByRole(role);
        assertEquals(1, teamMemberRoleList.size());
    }


    @Test
    void testAddMemberRoleError() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        UserDTO userDTO = new UserDTO();
        TeamDTO teamDTO = new TeamDTO();
        MembershipDto membershipDto = new MembershipDto(userDTO, teamDTO, "");

        Set<ConstraintViolation<UserDTO>> userViolations = validator.validate(userDTO);
        assertEquals(1, userViolations.size());
        Set<ConstraintViolation<TeamDTO>> teamViolations = validator.validate(teamDTO);
        assertEquals(1, teamViolations.size());
    }

    @Test
    void testAddMemberRoleDefaultRole() {
        Role role = new Role();
        LocalDateTime today = LocalDate.of(2022, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(today.atZone(ZoneId.of("UTC")).toInstant()));
        role.setId(123L);
        role.setName("Developer");
        Optional<Role> optionalRoleToFind = Optional.of(role);

        when(roleRepository.save(any())).thenReturn(optionalRoleToFind);
        when(roleRepository.findByName(any())).thenReturn(optionalRoleToFind);

        UserDTO userDTO = new UserDTO("371d2ee8-cdf4-48cf-9ddb-04798b79ad9e");
        TeamDTO teamDTO = new TeamDTO("7676a4bf-adfe-415c-941b-1739af07039b");
        MembershipDto membershipDto = new MembershipDto(userDTO, teamDTO, "");
        TeamMemberRole teamMemberRoleToVerify = new TeamMemberRole("22", "22", optionalRoleToFind.get());
        when(teamMemberRoleRepository.save(any())).thenReturn(teamMemberRoleToVerify);
        TeamMemberRole teamMemberRoleSaved = teamMemberRoleServiceImpl.addMemberRole(membershipDto);

        assertSame(teamMemberRoleToVerify, teamMemberRoleSaved);
    }

    @Test
    void testFindMemberships() {
        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setId(123L);
        role.setName("Tester");
        Optional<Role> optionalRole = Optional.of(role);

        when(roleRepository.findByName(any())).thenReturn(optionalRole);
        TeamMemberRole teamMemberRole = new TeamMemberRole("371d2ee8-cdf4-48cf-9ddb-04798b79ad9e", "7676a4bf-adfe-415c-941b-1739af07039b");
        teamMemberRole.setRole(optionalRole.get());
        when(teamMemberRoleRepository.save(any())).thenReturn(teamMemberRole);
        when(teamMemberRoleRepository.findByRole(any())).thenReturn(Arrays.asList(teamMemberRole));


        List<MembershipDto> teamMemberRoleList = teamMemberRoleServiceImpl.findMemberships(role.getName());
        assertEquals("371d2ee8-cdf4-48cf-9ddb-04798b79ad9e", teamMemberRoleList.get(0).getUser().getId());

    }
}

