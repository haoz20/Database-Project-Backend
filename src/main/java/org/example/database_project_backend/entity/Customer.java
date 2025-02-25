package org.example.database_project_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private Integer customerId;

    private int studiospherePoints;

    // Establish a one-to-one relationship with User.
    // Assuming the customer_id is also the user_id in the User table.
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "customer_id")
    private User user;
}