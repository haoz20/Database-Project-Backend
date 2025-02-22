package org.example.database_project_backend.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityId implements Serializable {
    private Integer photographer;
    private LocalDate availableDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailabilityId that = (AvailabilityId) o;
        return Objects.equals(photographer, that.photographer) &&
                Objects.equals(availableDate, that.availableDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photographer, availableDate);
    }
}
