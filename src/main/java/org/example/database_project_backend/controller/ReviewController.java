package org.example.database_project_backend.controller;

import org.example.database_project_backend.dto.ReviewDTO;
import org.example.database_project_backend.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByPhotographer(@PathVariable Integer photographerId) {
        return ResponseEntity.ok(reviewService.getReviewsByPhotographer(photographerId));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.addReview(reviewDTO));
    }

    @DeleteMapping("/{bookingId}/{customerId}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer bookingId, @PathVariable Integer customerId) {
        reviewService.deleteReview(bookingId, customerId);
        return ResponseEntity.ok("Review deleted successfully.");
    }
}

