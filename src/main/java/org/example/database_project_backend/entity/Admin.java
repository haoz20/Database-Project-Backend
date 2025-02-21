package org.example.database_project_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "admin_id")
    private Integer adminId;

    private String permission;
}