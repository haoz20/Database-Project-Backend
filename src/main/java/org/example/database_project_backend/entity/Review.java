package org.example.database_project_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
@IdClass(ReviewId.class)  // ✅ Use composite key
public class Review {

    @Id
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;  // ✅ Each review is linked to a specific booking

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;  // ✅ Foreign key to Customer

    @Column(name = "feedback", nullable = false, columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "rate", nullable = false)
    private Integer rate;  // ✅ Rating between 1 and 5
}

