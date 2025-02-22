package org.example.database_project_backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Integer bookingId;
    private Integer customerId;
    private String feedback;
    private Integer rate;
}

