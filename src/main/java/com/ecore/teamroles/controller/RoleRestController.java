package com.ecore.teamroles.controller;

import com.ecore.teamroles.dto.MembershipDto;
import com.ecore.teamroles.model.Role;
import com.ecore.teamroles.service.RoleService;
import com.ecore.teamroles.service.TeamMemberRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private TeamMemberRoleService teamMemberRoleService;

    @PostMapping(value = "/addRole")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addRole(@RequestBody Role role) {
        Role roleAdded = roleService.addRole(role);
        if (roleAdded != null) {
            return ResponseEntity.ok(roleAdded);
        }
        return new ResponseEntity<>("Role ´" + role.getName() + "' already exist", HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<Role> listRoles() {
        return roleService.listRoles();
    }

    @GetMapping(value = "/roles/{roleName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Role getUserByUsername(@PathVariable String roleName) {
        Optional<Role> role = roleService.getRoleByName(roleName);
        if (role.isPresent()) {
            return role.get();
        }
        return null;
    }

    @PostMapping(value = "/addMemberRole", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addMemberRole(@RequestBody MembershipDto membershipDto) {
        return ResponseEntity.ok(teamMemberRoleService.addMemberRole(membershipDto));
    }

    @PostMapping(value = "/findMemberships")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> findMemberships(@RequestParam("roleName") String roleName) {
        return ResponseEntity.ok(teamMemberRoleService.findMemberships(roleName));
    }

}
