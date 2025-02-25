package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.analytics.UsersAnalyticsDTO;
import org.example.database_project_backend.dto.analytics.BookingsAnalyticsDTO;
import org.example.database_project_backend.dto.analytics.PhotographerAnalyticsDTO;
import org.example.database_project_backend.entity.constants.Role;
import org.example.database_project_backend.repository.UserRepository;
import org.example.database_project_backend.repository.BookingRepository;
import org.example.database_project_backend.repository.PhotographerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class AnalyticsService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final PhotographerRepository photographerRepository;

    public AnalyticsService(UserRepository userRepository, BookingRepository bookingRepository, PhotographerRepository photographerRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.photographerRepository = photographerRepository;
    }

    public UsersAnalyticsDTO getUsersAnalytics() {
        long totalPhotographers = userRepository.countByRole(Role.PHOTOGRAPHER);
        long totalCustomers = userRepository.countByRole(Role.CUSTOMER);
        return new UsersAnalyticsDTO(totalPhotographers, totalCustomers);
    }


    public BookingsAnalyticsDTO getBookingsAnalytics() {
        long totalBookings = bookingRepository.count();
        LocalDate today = LocalDate.now();
        long upcomingBookings = bookingRepository.countByEventDateAfter(today);
        long cancelledBookings = bookingRepository.countByBookingStatus("Cancelled");
        String mostActiveBookingDay = bookingRepository.findMostActiveBookingDay();
        return new BookingsAnalyticsDTO(totalBookings, upcomingBookings, cancelledBookings, mostActiveBookingDay);
    }

    @Transactional(readOnly = true)
    public List<PhotographerAnalyticsDTO> getTopPhotographers() {
        // Fetch top 5 photographers
        return photographerRepository.findTopPhotographers(PageRequest.of(0, 5));
    }
}

