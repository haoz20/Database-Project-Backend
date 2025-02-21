package org.example.database_project_backend.repository;

import org.example.database_project_backend.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotographerRepository extends JpaRepository<Photographer, Integer> {
}