package kz.tech.nuverse.controller;

import jakarta.validation.Valid;
import kz.tech.nuverse.model.dto.UniversityDTO;
import kz.tech.nuverse.model.dto.create.UniversityCreateDTO;
import kz.tech.nuverse.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/universities")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping
    public ResponseEntity<UniversityDTO> createUniversity(@RequestBody @Valid UniversityCreateDTO universityCreateDTO) {
        return new ResponseEntity<>(universityService.createUniversity(universityCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDTO> getUniversityById(@PathVariable UUID id) {
        return ResponseEntity.ok(universityService.getUniversityById(id));
    }

    @GetMapping
    public ResponseEntity<List<UniversityDTO>> getAllUniversities() {
        return ResponseEntity.ok(universityService.getAllUniversities());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UniversityDTO> updateUniversity(
            @PathVariable UUID id,
            @RequestBody @Valid UniversityCreateDTO universityCreateDTO) {
        return ResponseEntity.ok(universityService.updateUniversity(id, universityCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable UUID id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UniversityDTO>> searchUniversities(@RequestParam String keyword) {
        return ResponseEntity.ok(universityService.searchUniversities(keyword));
    }
}
