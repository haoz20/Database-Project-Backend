package org.example.database_project_backend.repository;

import org.example.database_project_backend.dto.analytics.PhotographerAnalyticsDTO;
import org.example.database_project_backend.entity.Photographer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PhotographerRepository extends JpaRepository<Photographer, Integer> {
    @Query("SELECT new org.example.database_project_backend.dto.analytics.PhotographerAnalyticsDTO(u.name, u.email, COUNT(b)) " +
            "FROM Photographer p JOIN p.user u " +
            "LEFT JOIN Booking b ON b.photographer.photographerId = p.photographerId AND b.bookingStatus = 'Booked' " +
            "GROUP BY u.name, u.email " +
            "ORDER BY COUNT(b) DESC")
    List<PhotographerAnalyticsDTO> findTopPhotographers(Pageable pageable);
}
