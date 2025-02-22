package org.example.database_project_backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDTO {
    private Integer photographerId;
    private LocalDate availableDate;
    private String status;
}
