package org.example.database_project_backend.dto;

import lombok.*;
import org.example.database_project_backend.entity.constants.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private Role role;
}
