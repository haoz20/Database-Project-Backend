package org.example.database_project_backend.repository;

import org.example.database_project_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomerCustomerId(Integer customerId);
    List<Booking> findByPhotographerPhotographerId(Integer photographerId);
    long countByEventDateAfter(LocalDate date);
    long countByBookingStatus(String status);

    // Use a native query to get the day (formatted) with the highest number of bookings.
    @Query(value = "SELECT to_char(event_date, 'YYYY/MM/DD') as booking_day " +
            "FROM booking GROUP BY booking_day ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    String findMostActiveBookingDay();
}
