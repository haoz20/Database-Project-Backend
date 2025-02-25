package org.example.database_project_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.config.CustomEditorConfigurer;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Integer issueId;

    @ManyToOne
    @JoinColumn(name = "reported_by", nullable = false)
    private Customer reportedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private Admin assignedTo;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "issue_type", nullable = false)
    private String issueType;

    @Column(name = "issue_status", nullable = false)
    private String issueStatus = "New";

    @Column(name = "resolution_detail", columnDefinition = "TEXT")
    private String resolutionDetail;

    @Column(name = "reported_at")
    private LocalDate reportedAt = LocalDate.now();

    // Optional transient fields for convenience when converting to DTO
    @Transient
    private String customerName;

    @Transient
    private String customerEmail;

    @PostLoad
    public void populateTransientFields() {
        if (reportedBy != null && reportedBy.getUser() != null) {
            this.customerName = reportedBy.getUser().getName();
            this.customerEmail = reportedBy.getUser().getEmail();
        }
    }

}

