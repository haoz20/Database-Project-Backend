package org.example.database_project_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.database_project_backend.entity.constants.Speciality;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerDTO {
    private Integer photographerId;
    private String name;
    private String email;
    private Speciality speciality;
    private List<String> category;
    private String portfolio;
    private List<String> availableToWorkIn;
    private Double rating;

    @JsonIgnore
    public String getCategoryAsString() {
        return category != null ? String.join(",", category) : null;
    }

    @JsonIgnore
    public String getAvailableToWorkInAsString() {
        return availableToWorkIn != null ? String.join(",", availableToWorkIn) : null; // ✅ Convert List → String
    }

}
