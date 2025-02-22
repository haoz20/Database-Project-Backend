package org.example.database_project_backend.repository;

import org.example.database_project_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomerCustomerId(Integer customerId);
    List<Booking> findByPhotographerPhotographerId(Integer photographerId);
}
