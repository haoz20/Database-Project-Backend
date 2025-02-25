package org.example.database_project_backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer bookingId;
    private Integer customerId;
    private Integer photographerId;
    private LocalDate eventDate;
    private String eventLocation;
    private String bookingStatus;
    private String speciality;
    private String category;
    private String customerName;
    private String photographerName;
}
