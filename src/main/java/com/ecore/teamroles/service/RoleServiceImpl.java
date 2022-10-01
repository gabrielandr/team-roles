package com.ecore.teamroles.service;

import com.ecore.teamroles.model.Role;
import com.ecore.teamroles.repository.RoleRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

//    @Value("${keycloak.token-endpoint}")
//    private String tokenEndpoint;

    private final RoleRepository roleRepository;

    public static final Gson gson = new Gson();

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(Role role) {
        Optional<Role> roleToAdd = roleRepository.findByName(role.getName());
        if (!roleToAdd.isPresent()) {
            return roleRepository.save(role);
        }
        return null;
    }

    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }
}
