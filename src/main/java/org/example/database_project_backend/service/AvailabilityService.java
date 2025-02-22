package org.example.database_project_backend.service;

import org.example.database_project_backend.dto.AvailabilityDTO;
import org.example.database_project_backend.entity.Availability;
import org.example.database_project_backend.entity.Photographer;
import org.example.database_project_backend.repository.AvailabilityRepository;
import org.example.database_project_backend.repository.PhotographerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final PhotographerRepository photographerRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, PhotographerRepository photographerRepository) {
        this.availabilityRepository = availabilityRepository;
        this.photographerRepository = photographerRepository;
    }

    public List<AvailabilityDTO> getAvailabilityByPhotographer(Integer photographerId) {
        return availabilityRepository.findByPhotographerPhotographerId(photographerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AvailabilityDTO addAvailability(AvailabilityDTO availabilityDTO) {
        Photographer photographer = photographerRepository.findById(availabilityDTO.getPhotographerId())
                .orElseThrow(() -> new RuntimeException("Photographer not found with ID: " + availabilityDTO.getPhotographerId()));

        Availability availability = new Availability();
        availability.setPhotographer(photographer);
        availability.setAvailableDate(availabilityDTO.getAvailableDate());
        availability.setStatus("available"); // Default status when added

        return convertToDTO(availabilityRepository.save(availability));
    }

    @Transactional
    public AvailabilityDTO updateAvailability(Integer photographerId, LocalDate availableDate, String status) {
        Availability availability = availabilityRepository
                .findByPhotographerPhotographerIdAndAvailableDate(photographerId, availableDate)
                .orElseThrow(() -> new RuntimeException("Availability not found for photographer ID: " + photographerId + " and date: " + availableDate));

        availability.setStatus(status);

        return convertToDTO(availabilityRepository.save(availability));
    }


    @Transactional
    public void deleteAvailability(Integer photographerId, LocalDate availableDate) {
        Availability availability = availabilityRepository.findByPhotographerPhotographerIdAndAvailableDate(photographerId, availableDate)
                .orElseThrow(() -> new RuntimeException("Availability not found for date: " + availableDate));

        availabilityRepository.delete(availability);
    }

    private AvailabilityDTO convertToDTO(Availability availability) {
        return new AvailabilityDTO(
                availability.getPhotographer().getPhotographerId(),
                availability.getAvailableDate(),
                availability.getStatus()
        );
    }
}
