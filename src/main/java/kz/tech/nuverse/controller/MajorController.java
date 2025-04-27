package kz.tech.nuverse.controller;

import jakarta.validation.Valid;
import kz.tech.nuverse.model.dto.MajorDTO;
import kz.tech.nuverse.model.dto.create.MajorCreateDTO;
import kz.tech.nuverse.service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;

    @PostMapping
    public ResponseEntity<MajorDTO> createMajor(@RequestBody @Valid MajorCreateDTO majorCreateDTO) {
        return new ResponseEntity<>(majorService.createMajor(majorCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorDTO> getMajorById(@PathVariable UUID id) {
        return ResponseEntity.ok(majorService.getMajorById(id));
    }

    @GetMapping
    public ResponseEntity<List<MajorDTO>> getAllMajors() {
        return ResponseEntity.ok(majorService.getAllMajors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorDTO> updateMajor(@PathVariable UUID id, @RequestBody @Valid MajorCreateDTO majorCreateDTO) {
        return ResponseEntity.ok(majorService.updateMajor(id, majorCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMajor(@PathVariable UUID id) {
        majorService.deleteMajor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<MajorDTO>> searchMajors(@RequestParam String keyword) {
        return ResponseEntity.ok(majorService.searchMajors(keyword));
    }
}
