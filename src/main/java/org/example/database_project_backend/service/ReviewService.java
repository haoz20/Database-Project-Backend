package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.ReviewDTO;
import org.example.database_project_backend.entity.Booking;
import org.example.database_project_backend.entity.Customer;
import org.example.database_project_backend.entity.Review;
import org.example.database_project_backend.entity.ReviewId;
import org.example.database_project_backend.repository.BookingRepository;
import org.example.database_project_backend.repository.CustomerRepository;
import org.example.database_project_backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    public List<ReviewDTO> getReviewsByPhotographer(Integer photographerId) {
        return reviewRepository.findByPhotographer(photographerId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByCustomer(Integer customerId) {
        return reviewRepository.findByCustomerCustomerId(customerId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        Booking booking = bookingRepository.findById(reviewDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Customer customer = customerRepository.findById(reviewDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Review review = new Review(booking, customer, reviewDTO.getFeedback(), reviewDTO.getRate());
        return convertToDTO(reviewRepository.save(review));
    }

    @Transactional
    public ReviewDTO updateReview(Integer bookingId, Integer customerId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(new ReviewId(bookingId, customerId))
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setFeedback(reviewDTO.getFeedback());
        review.setRate(reviewDTO.getRate());
        return convertToDTO(reviewRepository.save(review));
    }

    @Transactional
    public void deleteReview(Integer bookingId, Integer customerId) {
        reviewRepository.deleteById(new ReviewId(bookingId, customerId));
    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(review.getBooking().getBookingId(), review.getCustomer().getCustomerId(),
                review.getFeedback(), review.getRate());
    }
}
