package com.ecore.teamroles.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Entity
public class TeamMemberRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @NotNull
    @NotBlank(message = "User ID cannot be blank")
    private String userId;
    @NotNull
    @NotBlank(message = "Team ID cannot be blank")
    private String teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    public TeamMemberRole(String userId, String teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    public TeamMemberRole(String userId, String teamId, Role role) {
        this.userId = userId;
        this.teamId = teamId;
        this.role = role;
    }
}
