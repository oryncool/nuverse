package kz.tech.nuverse.controller;

import jakarta.validation.Valid;
import kz.tech.nuverse.model.dto.PhonebookDTO;
import kz.tech.nuverse.model.dto.create.PhonebookCreateDTO;
import kz.tech.nuverse.service.PhonebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/phonebooks")
@RequiredArgsConstructor
public class PhonebookController {

    private final PhonebookService phonebookService;

    @PostMapping
    public ResponseEntity<PhonebookDTO> createPhonebook(@RequestBody @Valid PhonebookCreateDTO phonebookCreateDTO) {
        return new ResponseEntity<>(phonebookService.createPhonebook(phonebookCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhonebookDTO> getPhonebookById(@PathVariable UUID id) {
        return ResponseEntity.ok(phonebookService.getPhonebookById(id));
    }

    @GetMapping
    public ResponseEntity<List<PhonebookDTO>> getAllPhonebooks() {
        return ResponseEntity.ok(phonebookService.getAllPhonebooks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhonebookDTO> updatePhonebook(@PathVariable UUID id, @RequestBody @Valid PhonebookCreateDTO phonebookCreateDTO) {
        return ResponseEntity.ok(phonebookService.updatePhonebook(id, phonebookCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhonebook(@PathVariable UUID id) {
        phonebookService.deletePhonebook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PhonebookDTO>> searchPhonebooks(@RequestParam String keyword) {
        return ResponseEntity.ok(phonebookService.searchPhonebooks(keyword));
    }
}
