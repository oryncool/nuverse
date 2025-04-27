package kz.tech.nuverse.controller;

import jakarta.validation.Valid;
import kz.tech.nuverse.model.dto.ProfessorAppointmentDTO;
import kz.tech.nuverse.model.dto.create.ProfessorAppointmentCreateDTO;
import kz.tech.nuverse.service.ProfessorAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/professor-appointments")
@RequiredArgsConstructor
public class ProfessorAppointmentController {

    private final ProfessorAppointmentService service;

    @PostMapping
    public ResponseEntity<ProfessorAppointmentDTO> create(@RequestBody @Valid ProfessorAppointmentCreateDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorAppointmentDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorAppointmentDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorAppointmentDTO> update(@PathVariable UUID id, @RequestBody @Valid ProfessorAppointmentCreateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ProfessorAppointmentDTO>> findByProfessorId(@PathVariable UUID professorId) {
        return ResponseEntity.ok(service.findByProfessorId(professorId));
    }
}
