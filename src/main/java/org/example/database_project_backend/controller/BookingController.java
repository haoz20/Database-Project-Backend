package org.example.database_project_backend.controller;

import org.example.database_project_backend.dto.BookingDTO;
import org.example.database_project_backend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Get all bookings (for admin or overview)
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Get a specific booking by its ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // Create a new booking
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }

    // Update an existing booking
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Integer id, @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    // Delete a booking by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking with ID " + id + " deleted successfully.");
    }

    // Additional endpoint: Get all bookings for a specific photographer
    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByPhotographer(@PathVariable Integer photographerId) {
        return ResponseEntity.ok(bookingService.getBookingsByPhotographer(photographerId));
    }

    // Additional endpoint: Get all bookings for a specific customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(bookingService.getBookingsByCustomer(customerId));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<BookingDTO> confirmBooking(
            @PathVariable Integer id,
            @RequestParam Integer photographerId) {
        // The service method sets booking status to "Booked" if the photographer is authorized
        BookingDTO updated = bookingService.confirmBooking(id, photographerId);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingDTO> cancelBooking(
            @PathVariable Integer id,
            @RequestParam Integer photographerId) {
        // The service method sets booking status to "Cancelled" if the photographer is authorized
        BookingDTO updated = bookingService.cancelBooking(id, photographerId);
        return ResponseEntity.ok(updated);
    }

}
