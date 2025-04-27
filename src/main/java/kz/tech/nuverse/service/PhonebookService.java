package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.PhonebookDTO;
import kz.tech.nuverse.model.dto.create.PhonebookCreateDTO;

import java.util.List;
import java.util.UUID;

public interface PhonebookService {
    PhonebookDTO createPhonebook(PhonebookCreateDTO phonebookCreateDTO);
    PhonebookDTO getPhonebookById(UUID id);
    List<PhonebookDTO> getAllPhonebooks();
    PhonebookDTO updatePhonebook(UUID id, PhonebookCreateDTO phonebookCreateDTO);
    void deletePhonebook(UUID id);
    List<PhonebookDTO> searchPhonebooks(String keyword);
}
