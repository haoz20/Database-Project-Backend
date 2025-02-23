package org.example.database_project_backend.repository;

import org.example.database_project_backend.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByReportedBy_CustomerId(Integer customerId);
    List<Issue> findByAssignedTo_AdminId(Integer adminId);
}
