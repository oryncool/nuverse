package kz.tech.nuverse.controller;

import jakarta.validation.Valid;
import kz.tech.nuverse.model.dto.ProfessorExperienceDTO;
import kz.tech.nuverse.model.dto.create.ProfessorExperienceCreateDTO;
import kz.tech.nuverse.service.ProfessorExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/professor-experiences")
@RequiredArgsConstructor
public class ProfessorExperienceController {

    private final ProfessorExperienceService service;

    @PostMapping
    public ResponseEntity<ProfessorExperienceDTO> create(@RequestBody @Valid ProfessorExperienceCreateDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorExperienceDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorExperienceDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorExperienceDTO> update(@PathVariable UUID id, @RequestBody @Valid ProfessorExperienceCreateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ProfessorExperienceDTO>> findByProfessorId(@PathVariable UUID professorId) {
        return ResponseEntity.ok(service.findByProfessorId(professorId));
    }
}
