package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.PhonebookDTO;
import kz.tech.nuverse.model.dto.create.PhonebookCreateDTO;
import kz.tech.nuverse.model.entity.PhonebookEntity;
import kz.tech.nuverse.repository.PhonebookRepository;
import kz.tech.nuverse.service.PhonebookService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhonebookServiceImpl implements PhonebookService {

    private final PhonebookRepository phonebookRepository;

    @Override
    public PhonebookDTO createPhonebook(PhonebookCreateDTO phonebookCreateDTO) {
        PhonebookEntity entity = ModelMapperUtil.map(phonebookCreateDTO, PhonebookEntity.class);

        return ModelMapperUtil.map(phonebookRepository.save(entity), PhonebookDTO.class);
    }

    @Override
    public PhonebookDTO getPhonebookById(UUID id) {
        return phonebookRepository.findById(id)
                .map(e -> ModelMapperUtil.map(e, PhonebookDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Phonebook not found with id " + id));
    }

    @Override
    public List<PhonebookDTO> getAllPhonebooks() {
        return ModelMapperUtil.mapAll(phonebookRepository.findAll(), PhonebookDTO.class);
    }

    @Override
    public PhonebookDTO updatePhonebook(UUID id, PhonebookCreateDTO phonebookCreateDTO) {
        PhonebookEntity existing = phonebookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phonebook not found with id " + id));

        ModelMapperUtil.map(phonebookCreateDTO, existing);
        return ModelMapperUtil.map(phonebookRepository.save(existing), PhonebookDTO.class);
    }

    @Override
    public void deletePhonebook(UUID id) {
        phonebookRepository.deleteById(id);
    }

    @Override
    public List<PhonebookDTO> searchPhonebooks(String keyword) {
        return ModelMapperUtil.mapAll(
                phonebookRepository.findByPhoneContainingIgnoreCase(keyword),
                PhonebookDTO.class
        );
    }
}
