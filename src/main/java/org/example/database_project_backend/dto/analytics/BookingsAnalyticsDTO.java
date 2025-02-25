package org.example.database_project_backend.dto.analytics;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingsAnalyticsDTO {
    private long totalBookings;
    private long upcomingBookings;
    private long cancelledBookings;
    private String mostActiveBookingDay; // Format: YYYY/MM/DD
}

