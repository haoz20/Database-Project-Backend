package org.example.database_project_backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Integer issueId;
    private Integer reportedBy;
    private Integer assignedTo;
    private String description;
    private String issueType;
    private String issueStatus;
    private String resolutionDetail;
    private LocalDate reportedAt;
    private String customerName;
    private String customerEmail;
}

