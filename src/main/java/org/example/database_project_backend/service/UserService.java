package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.UserDTO;
import org.example.database_project_backend.entity.Admin;
import org.example.database_project_backend.entity.Customer;
import org.example.database_project_backend.entity.Photographer;
import org.example.database_project_backend.entity.User;
import org.example.database_project_backend.repository.AdminRepository;
import org.example.database_project_backend.repository.CustomerRepository;
import org.example.database_project_backend.repository.PhotographerRepository;
import org.example.database_project_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PhotographerRepository photographerRepository;
    private final AdminRepository adminRepository;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository,
                       PhotographerRepository photographerRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.photographerRepository = photographerRepository;
        this.adminRepository = adminRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        addUserToRoleSpecificTable(savedUser);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Integer id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                null,
                user.getRole()
        );
    }
    private User convertToEntity(UserDTO userDTO) {
        return new User(
                userDTO.getUserId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole()
        );
    }

    private void addUserToRoleSpecificTable(User user) {
        switch (user.getRole()) {
            case CUSTOMER:
                Customer customer = new Customer();
                customer.setCustomerId(user.getUserId());
                customerRepository.save(customer);
                break;
            case PHOTOGRAPHER:
                Photographer photographer = new Photographer();
                photographer.setPhotographerId(user.getUserId());
                // Set other photographer-specific fields here
                photographerRepository.save(photographer);
                break;
            case ADMIN:
                Admin admin = new Admin();
                admin.setAdminId(user.getUserId());
                // Set other admin-specific fields here
                adminRepository.save(admin);
                break;
        }
    }
}