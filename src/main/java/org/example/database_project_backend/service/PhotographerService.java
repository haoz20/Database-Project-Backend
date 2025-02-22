package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.PhotographerDTO;
import org.example.database_project_backend.entity.Photographer;
import org.example.database_project_backend.entity.User;
import org.example.database_project_backend.repository.PhotographerRepository;
import org.example.database_project_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotographerService {
    private final PhotographerRepository photographerRepository;
    private final UserRepository userRepository;

    public PhotographerService(PhotographerRepository photographerRepository, UserRepository userRepository) {
        this.photographerRepository = photographerRepository;
        this.userRepository = userRepository;
    }

    public List<PhotographerDTO> getAllPhotographers() {
        return photographerRepository.findAll().stream()
                .map(photographer -> {
                    User user = userRepository.findById(photographer.getPhotographerId())
                            .orElseThrow(() -> new RuntimeException("User not found for photographer ID: " + photographer.getPhotographerId()));
                    return convertToDTO(photographer, user);
                })
                .collect(Collectors.toList());
    }


    public PhotographerDTO getPhotographerById(Integer id) {
        Photographer photographer = photographerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photographer not found with id: " + id));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return convertToDTO(photographer, user);
    }

    @Transactional
    public PhotographerDTO updatePhotographer(Integer id, PhotographerDTO photographerDTO) {
        Photographer photographer = photographerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photographer not found with id: " + id));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        photographer.setSpeciality(photographerDTO.getSpeciality());
        photographer.setCategory(photographerDTO.getCategoryAsString());
        photographer.setPortfolio(photographerDTO.getPortfolio());
        photographer.setAvailableToWorkIn(photographerDTO.getAvailableToWorkInAsString());
        photographer.setRating(photographerDTO.getRating());

        user.setName(photographerDTO.getName());
        user.setEmail(photographerDTO.getEmail());

        photographerRepository.save(photographer);
        userRepository.save(user);

        return convertToDTO(photographer, user);
    }


    private PhotographerDTO convertToDTO(Photographer photographer, User user) {
        return new PhotographerDTO(
                photographer.getPhotographerId(),
                user.getName(),
                user.getEmail(),
                photographer.getSpeciality(),
                (photographer.getCategory() != null) ?
                        Arrays.asList(photographer.getCategory().split(",")) : null,
                photographer.getPortfolio(),
                (photographer.getAvailableToWorkIn() != null) ?
                        Arrays.asList(photographer.getAvailableToWorkIn().split(",")) : null,
                photographer.getRating()
        );
    }

}
