package org.example.database_project_backend.controller;

import org.example.database_project_backend.dto.analytics.UsersAnalyticsDTO;
import org.example.database_project_backend.dto.analytics.BookingsAnalyticsDTO;
import org.example.database_project_backend.dto.analytics.PhotographerAnalyticsDTO;
import org.example.database_project_backend.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/users")
    public ResponseEntity<UsersAnalyticsDTO> getUsersAnalytics() {
        return ResponseEntity.ok(analyticsService.getUsersAnalytics());
    }

    @GetMapping("/bookings")
    public ResponseEntity<BookingsAnalyticsDTO> getBookingsAnalytics() {
        return ResponseEntity.ok(analyticsService.getBookingsAnalytics());
    }

    @GetMapping("/top-photographers")
    public ResponseEntity<List<PhotographerAnalyticsDTO>> getTopPhotographers() {
        return ResponseEntity.ok(analyticsService.getTopPhotographers());
    }
}

