package org.example.database_project_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.database_project_backend.entity.constants.Speciality;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photographer")
public class Photographer {
    @Id
    @Column(name = "photographer_id")
    private Integer photographerId;

    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @Column(name = "category", columnDefinition = "Text",nullable = true)
    private String category;

    private String portfolio;

    @Column(name = "available_to_work_in", columnDefinition = "TEXT", nullable = true)
    private String availableToWorkIn;

    @Column(name = "rating", nullable = true)
    private Double rating;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "photographer_id")
    private User user;

    @OneToMany(mappedBy = "photographer")
    private List<Booking> bookings;


}
