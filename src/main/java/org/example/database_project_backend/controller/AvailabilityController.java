package org.example.database_project_backend.controller;

import org.example.database_project_backend.dto.AvailabilityDTO;
import org.example.database_project_backend.service.AvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/{photographerId}")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilityByPhotographer(@PathVariable Integer photographerId) {
        return ResponseEntity.ok(availabilityService.getAvailabilityByPhotographer(photographerId));
    }

    @PostMapping
    public ResponseEntity<AvailabilityDTO> addAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
        return ResponseEntity.ok(availabilityService.addAvailability(availabilityDTO));
    }

    @PutMapping("/{photographerId}")
    public ResponseEntity<AvailabilityDTO> updateAvailabilityStatus(
            @PathVariable Integer photographerId,
            @RequestBody AvailabilityDTO availabilityDTO) {

        return ResponseEntity.ok(availabilityService.updateAvailability(
                photographerId,
                availabilityDTO.getAvailableDate(),
                availabilityDTO.getStatus()
        ));
    }



    @DeleteMapping
    public ResponseEntity<String> deleteAvailability(
            @RequestParam Integer photographerId,
            @RequestParam String availableDate) {

        LocalDate date = LocalDate.parse(availableDate);
        availabilityService.deleteAvailability(photographerId, date);
        return ResponseEntity.ok("Availability deleted successfully.");
    }
}

