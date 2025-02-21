package org.example.database_project_backend.controller;

import org.example.database_project_backend.dto.PhotographerDTO;
import org.example.database_project_backend.service.PhotographerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photographers")
public class PhotographerController {
    private final PhotographerService photographerService;

    public PhotographerController(PhotographerService photographerService) {
        this.photographerService = photographerService;
    }

    @GetMapping
    public ResponseEntity<List<PhotographerDTO>> getAllPhotographers() {
        return ResponseEntity.ok(photographerService.getAllPhotographers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotographerDTO> getPhotographerById(@PathVariable Integer id) {
        return ResponseEntity.ok(photographerService.getPhotographerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhotographerDTO> updatePhotographer(@PathVariable Integer id, @RequestBody PhotographerDTO photographerDTO) {
        return ResponseEntity.ok(photographerService.updatePhotographer(id, photographerDTO));
    }

}