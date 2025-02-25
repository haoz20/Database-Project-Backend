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

    @Transactional
    public User updateUser(Integer id, User userUpdates) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userUpdates.getName());
        existingUser.setEmail(userUpdates.getEmail());
        existingUser.setRole(userUpdates.getRole());

        // Only update the password if a new value is provided
        if (userUpdates.getPassword() != null && !userUpdates.getPassword().trim().isEmpty()) {
            existingUser.setPassword(userUpdates.getPassword());
        }

        return userRepository.save(existingUser);
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
                user.getPassword(),
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
        // Instead of manually setting the ID, we associate the User.
        switch (user.getRole()) {
            case CUSTOMER:
                Customer customer = new Customer();
                customer.setUser(user);
                customerRepository.save(customer);
                break;
            case PHOTOGRAPHER:
                Photographer photographer = new Photographer();
                photographer.setUser(user);
                photographerRepository.save(photographer);
                break;
            case ADMIN:
                Admin admin = new Admin();
                admin.setUser(user);
                adminRepository.save(admin);
                break;
        }
    }
}
