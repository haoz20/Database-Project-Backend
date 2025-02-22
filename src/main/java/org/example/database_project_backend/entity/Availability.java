package org.example.database_project_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "availability")
@IdClass(AvailabilityId.class)
public class Availability implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "photographer_id", nullable = false)
    private Photographer photographer;

    @Id
    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @Column(name = "status", nullable = false)
    private String status;
}
