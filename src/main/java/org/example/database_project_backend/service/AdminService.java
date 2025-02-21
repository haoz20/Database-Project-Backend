package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.AdminDTO;
import org.example.database_project_backend.entity.Admin;
import org.example.database_project_backend.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdminDTO getAdminById(Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        return convertToDTO(admin);
    }

    private AdminDTO convertToDTO(Admin admin) {
        return new AdminDTO(admin.getAdminId(), admin.getPermission());
    }
}
