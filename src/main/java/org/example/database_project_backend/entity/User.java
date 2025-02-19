package org.example.database_project_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.database_project_backend.entity.constants.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Integer userId;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}