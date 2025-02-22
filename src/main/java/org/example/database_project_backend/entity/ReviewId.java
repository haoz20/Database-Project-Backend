package org.example.database_project_backend.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewId implements Serializable {
    private Integer booking;
    private Integer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(booking, reviewId.booking) &&
                Objects.equals(customer, reviewId.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, customer);
    }
}

