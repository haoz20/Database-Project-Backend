package org.example.database_project_backend.repository;

import org.example.database_project_backend.entity.Review;
import org.example.database_project_backend.entity.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

    List<Review> findByCustomerCustomerId(Integer customerId);

    @Query("SELECT r FROM Review r JOIN r.booking b WHERE b.photographer.photographerId = :photographerId")
    List<Review> findByPhotographer(@Param("photographerId") Integer photographerId);

    @Query("SELECT AVG(r.rate) FROM Review r JOIN r.booking b WHERE b.photographer.photographerId = :photographerId")
    Double findAverageRatingByPhotographer(@Param("photographerId") Integer photographerId);
}

