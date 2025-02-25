package org.example.database_project_backend.repository;

import org.example.database_project_backend.entity.User;
import org.example.database_project_backend.entity.constants.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, Integer> {
    long countByRole(Role role);
}
