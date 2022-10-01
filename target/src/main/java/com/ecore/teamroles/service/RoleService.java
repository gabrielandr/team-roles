package com.ecore.teamroles.service;

import com.ecore.teamroles.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role addRole(Role role);

    List<Role> listRoles();

    Optional<Role> getRoleById(Long roleId);

//    UserRepresentation[] getUsers();
//
//    String getUserByUsername(String username);
//
//    UserRepresentation getUserById(String id);
//
//    boolean editUser(UserRepresentation userRepresentation);
//
//    Boolean resetPassword(String id, CredentialRepresentation credentialRepresentation);
//
//    String generateToken();
//
//    Boolean assignRoleToUser(String userId, String clientId, List<RoleRepresentation> roleRepresentations);
//
//    Integer userCount();
//
//    LinkedHashMap login(String loginInfo);
//
//    Boolean logout(String userId);
}
