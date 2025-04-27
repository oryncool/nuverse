package kz.tech.nuverse.controller;

import jakarta.validation.Valid;
import kz.tech.nuverse.model.dto.ProfessorDTO;
import kz.tech.nuverse.model.dto.create.ProfessorCreateDTO;
import kz.tech.nuverse.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@RequestBody @Valid ProfessorCreateDTO professorCreateDTO) {
        return new ResponseEntity<>(professorService.createProfessor(professorCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable UUID id) {
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> updateProfessor(@PathVariable UUID id, @RequestBody @Valid ProfessorCreateDTO professorCreateDTO) {
        return ResponseEntity.ok(professorService.updateProfessor(id, professorCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable UUID id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProfessorDTO>> searchProfessors(@RequestParam String keyword) {
        return ResponseEntity.ok(professorService.searchProfessors(keyword));
    }
}
