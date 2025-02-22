package org.example.database_project_backend.repository;

import org.example.database_project_backend.entity.Availability;
import org.example.database_project_backend.entity.AvailabilityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, AvailabilityId> {
    List<Availability> findByPhotographerPhotographerId(Integer photographerId);
    Optional<Availability> findByPhotographerPhotographerIdAndAvailableDate(Integer photographerId, LocalDate availableDate);
}
