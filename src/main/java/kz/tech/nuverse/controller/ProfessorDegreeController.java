package kz.tech.nuverse.controller;

import jakarta.validation.Valid;
import kz.tech.nuverse.model.dto.ProfessorDegreeDTO;
import kz.tech.nuverse.model.dto.create.ProfessorDegreeCreateDTO;
import kz.tech.nuverse.service.ProfessorDegreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/professor-degrees")
@RequiredArgsConstructor
public class ProfessorDegreeController {

    private final ProfessorDegreeService service;

    @PostMapping
    public ResponseEntity<ProfessorDegreeDTO> create(@RequestBody @Valid ProfessorDegreeCreateDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDegreeDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDegreeDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDegreeDTO> update(@PathVariable UUID id, @RequestBody @Valid ProfessorDegreeCreateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ProfessorDegreeDTO>> findByProfessorId(@PathVariable UUID professorId) {
        return ResponseEntity.ok(service.findByProfessorId(professorId));
    }
}
