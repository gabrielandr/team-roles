package com.ecore.teamroles.controller;

import com.ecore.teamroles.model.Role;
import com.ecore.teamroles.service.RoleService;
import com.ecore.teamroles.service.TeamMemberRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RoleRestController.class})
@ExtendWith(SpringExtension.class)
class RoleRestControllerTest {
    @Autowired
    private RoleRestController roleRestController;

    @MockBean
    private RoleService roleService;

    @MockBean
    private TeamMemberRoleService teamMemberRoleService;

    /**
     * Method under test: {@link RoleRestController#addRole(Role)}
     */
    @Test
    void testAddRole() throws Exception {
        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setId(123L);
        role.setName("Name");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        role.setVersion(1L);
        when(roleService.addRole((Role) any())).thenReturn(role);

        Role role1 = new Role();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        role1.setId(123L);
        role1.setName("Name");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        role1.setVersion(1L);
        String content = (new ObjectMapper()).writeValueAsString(role1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/addRole")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(roleRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"version\":1,\"createdAt\":0,\"updatedAt\":0,\"id\":123,\"name\":\"Name\"}"));
    }
}

