package org.example.database_project_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "photographer_id", nullable = false)
    private Photographer photographer;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "event_location", nullable = false)
    private String eventLocation;

    @Column(name = "booking_status", nullable = false)
    private String bookingStatus;

    @Column(name = "speciality", nullable = false)
    private String speciality;

    @Column(name = "category", nullable = false)
    private String category;

}

