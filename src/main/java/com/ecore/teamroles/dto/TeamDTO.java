package com.ecore.teamroles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TeamDTO {

    @NotBlank(message = "Team Id cannot be empty")
    private String id;
    private String name;
    private String teamLeadId;
    private List<String> teamMemberIds;

    public TeamDTO(String id) {
        this.id = id;
    }
}
