package com.ecore.teamroles.repository;

import com.ecore.teamroles.model.Role;
import com.ecore.teamroles.model.TeamMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRoleRepository extends JpaRepository<TeamMemberRole, Long> {

    List<TeamMemberRole> findByRole(Role role);
}
