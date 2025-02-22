package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.BookingDTO;
import org.example.database_project_backend.entity.Booking;
import org.example.database_project_backend.entity.Customer;
import org.example.database_project_backend.entity.Photographer;
import org.example.database_project_backend.repository.BookingRepository;
import org.example.database_project_backend.repository.CustomerRepository;
import org.example.database_project_backend.repository.PhotographerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final PhotographerRepository photographerRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, PhotographerRepository photographerRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.photographerRepository = photographerRepository;
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        return convertToDTO(booking);
    }

    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Customer customer = customerRepository.findById(bookingDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + bookingDTO.getCustomerId()));

        Photographer photographer = photographerRepository.findById(bookingDTO.getPhotographerId())
                .orElseThrow(() -> new RuntimeException("Photographer not found with ID: " + bookingDTO.getPhotographerId()));

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setPhotographer(photographer);
        booking.setEventDate(bookingDTO.getEventDate());
        booking.setEventLocation(bookingDTO.getEventLocation());
        booking.setBookingStatus(bookingDTO.getBookingStatus() != null ? bookingDTO.getBookingStatus() : "Pending");

        Booking savedBooking = bookingRepository.save(booking);
        return convertToDTO(savedBooking);
    }

    @Transactional
    public BookingDTO updateBooking(Integer id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        // ✅ Only update non-null fields
        if (bookingDTO.getEventDate() != null) {
            booking.setEventDate(bookingDTO.getEventDate());
        }

        if (bookingDTO.getEventLocation() != null) {
            booking.setEventLocation(bookingDTO.getEventLocation());
        }

        if (bookingDTO.getBookingStatus() != null) {
            booking.setBookingStatus(bookingDTO.getBookingStatus());
        }

        Booking updatedBooking = bookingRepository.save(booking);
        return convertToDTO(updatedBooking);
    }

    public void deleteBooking(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        bookingRepository.delete(booking);
    }

    private BookingDTO convertToDTO(Booking booking) {
        return new BookingDTO(
                booking.getBookingId(),
                booking.getCustomer().getCustomerId(),
                booking.getPhotographer().getPhotographerId(),
                booking.getEventDate(),
                booking.getEventLocation(),
                booking.getBookingStatus()
        );
    }
}
