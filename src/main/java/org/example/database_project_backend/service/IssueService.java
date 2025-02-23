package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.IssueDTO;
import org.example.database_project_backend.entity.Admin;
import org.example.database_project_backend.entity.Customer;
import org.example.database_project_backend.entity.Issue;
import org.example.database_project_backend.repository.AdminRepository;
import org.example.database_project_backend.repository.CustomerRepository;
import org.example.database_project_backend.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    public IssueService(IssueRepository issueRepository, CustomerRepository customerRepository, AdminRepository adminRepository) {
        this.issueRepository = issueRepository;
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
    }

    public List<IssueDTO> getAllIssues() {
        return issueRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public IssueDTO getIssueById(Integer id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        return convertToDTO(issue);
    }

    public IssueDTO createIssue(IssueDTO issueDTO) {
        Customer customer = customerRepository.findById(issueDTO.getReportedBy())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Issue issue = new Issue();
        issue.setReportedBy(customer);
        issue.setDescription(issueDTO.getDescription());
        issue.setIssueType(issueDTO.getIssueType());
        issue.setIssueStatus("New"); // Default status

        return convertToDTO(issueRepository.save(issue));
    }

    public IssueDTO updateIssue(Integer id, IssueDTO issueDTO) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        if (issueDTO.getAssignedTo() != null) {
            Admin admin = adminRepository.findById(issueDTO.getAssignedTo())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
            issue.setAssignedTo(admin);
        }

        issue.setIssueStatus(issueDTO.getIssueStatus());
        issue.setResolutionDetail(issueDTO.getResolutionDetail());
        issue.setResolutionDate(issueDTO.getResolutionDate());

        return convertToDTO(issueRepository.save(issue));
    }

    public void deleteIssue(Integer id) {
        issueRepository.deleteById(id);
    }

    private IssueDTO convertToDTO(Issue issue) {
        return new IssueDTO(
                issue.getIssueId(),
                issue.getReportedBy().getCustomerId(),
                issue.getAssignedTo() != null ? issue.getAssignedTo().getAdminId() : null,
                issue.getDescription(),
                issue.getIssueType(),
                issue.getIssueStatus(),
                issue.getResolutionDetail(),
                issue.getResolutionDate()
        );
    }
}
