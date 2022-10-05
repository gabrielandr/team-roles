package com.ecore.teamroles.service;

import com.ecore.teamroles.model.Role;
import com.ecore.teamroles.repository.RoleRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RoleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    /**
     * Method to test adding a new role, find the role inserted and then test adding a duplicated role
     */
    @Test
    void testAddRoleASuccess() {
        Role role = new Role();
        LocalDateTime today = LocalDate.of(2022, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(today.atZone(ZoneId.of("UTC")).toInstant()));
        role.setId(123L);
        role.setName("Tester");

        Optional<Role> optionalRoleToFind = Optional.of(role);

        when(roleRepository.save(any())).thenReturn(role);
        when(roleRepository.findByName(any())).thenReturn(optionalRoleToFind);

        Role duplicatedRole = new Role();
        LocalDateTime today4 = LocalDate.of(2022, 1, 1).atStartOfDay();
        duplicatedRole.setCreatedAt(Date.from(today4.atZone(ZoneId.of("UTC")).toInstant()));
        duplicatedRole.setId(123L);
        duplicatedRole.setName("Tester");
        assertNull(roleServiceImpl.addRole(duplicatedRole));
        verify(roleRepository).findByName(any());

    }


    @Test
    void testAddRoleEmptyName() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Role role = new Role();
        LocalDateTime today = LocalDate.of(2022, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(today.atZone(ZoneId.of("UTC")).toInstant()));
        role.setId(123L);
        role.setName("");

        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertEquals(1, violations.size());
    }


    @Test
    void testListRoles() {
        ArrayList<Role> roleList = new ArrayList<>();
        when(roleRepository.findAll()).thenReturn(roleList);
        List<Role> actualListRolesResult = roleServiceImpl.listRoles();
        assertSame(roleList, actualListRolesResult);
        assertEquals(true, actualListRolesResult.isEmpty());
        verify(roleRepository).findAll();
    }

    @Test
    void testGetRoleById() {
        Role roleById = new Role();
        LocalDateTime today = LocalDate.of(2022, 1, 1).atStartOfDay();
        roleById.setCreatedAt(Date.from(today.atZone(ZoneId.of("UTC")).toInstant()));
        roleById.setId(123L);
        roleById.setName("Tester");
        Optional<Role> optionalRole = Optional.of(roleById);

        when(roleRepository.findById(any())).thenReturn(optionalRole);
        Optional<Role> actualRoleById = roleServiceImpl.getRoleById(123L);
        assertSame(optionalRole, actualRoleById);
        assertEquals(true, actualRoleById.isPresent());
        verify(roleRepository).findById(any());
    }

    @Test
    void testGetRoleByName() {
        Role roleByName = new Role();
        LocalDateTime today = LocalDate.of(2022, 1, 1).atStartOfDay();
        roleByName.setCreatedAt(Date.from(today.atZone(ZoneId.of("UTC")).toInstant()));
        roleByName.setId(123L);
        roleByName.setName("Tester");
        Optional<Role> optionalRole = Optional.of(roleByName);

        when(roleRepository.findByName(any())).thenReturn(optionalRole);
        Optional<Role> actualRoleByName = roleServiceImpl.getRoleByName("Tester");
        assertSame(optionalRole, actualRoleByName);
        assertEquals(true, actualRoleByName.isPresent());
    }

    @Test
    void testGetRoleByNameEmpty() {
        Role roleByName = new Role();
        LocalDateTime today = LocalDate.of(2022, 1, 1).atStartOfDay();
        roleByName.setCreatedAt(Date.from(today.atZone(ZoneId.of("UTC")).toInstant()));
        roleByName.setId(123L);
        roleByName.setName("");
        Optional<Role> optionalRole = Optional.of(roleByName);

        Optional<Role> actualRoleByName = roleServiceImpl.getRoleByName(roleByName.getName());
        assertEquals(false, actualRoleByName.isPresent());
    }
}

