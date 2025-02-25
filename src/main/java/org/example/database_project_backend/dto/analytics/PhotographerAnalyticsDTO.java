package org.example.database_project_backend.dto.analytics;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotographerAnalyticsDTO {
    private String name;
    private String email;
    private long bookingsAccepted;
}
