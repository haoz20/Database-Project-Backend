package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.CustomerDTO;
import org.example.database_project_backend.entity.Customer;
import org.example.database_project_backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        return new CustomerDTO(customer.getCustomerId(), customer.getStudiospherePoints());
    }
}
