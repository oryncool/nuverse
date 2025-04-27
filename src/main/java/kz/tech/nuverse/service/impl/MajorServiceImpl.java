package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.MajorDTO;
import kz.tech.nuverse.model.dto.create.MajorCreateDTO;
import kz.tech.nuverse.model.entity.MajorEntity;
import kz.tech.nuverse.repository.MajorRepository;
import kz.tech.nuverse.service.MajorService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;

    @Override
    public MajorDTO createMajor(MajorCreateDTO majorCreateDTO) {
        MajorEntity entity = ModelMapperUtil.map(majorCreateDTO, MajorEntity.class);

        return ModelMapperUtil.map(majorRepository.save(entity), MajorDTO.class);
    }

    @Override
    public MajorDTO getMajorById(UUID id) {
        return majorRepository.findById(id)
                .map(e -> ModelMapperUtil.map(e, MajorDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Major not found with id " + id));
    }

    @Override
    public List<MajorDTO> getAllMajors() {
        return ModelMapperUtil.mapAll(majorRepository.findAll(), MajorDTO.class);
    }

    @Override
    public MajorDTO updateMajor(UUID id, MajorCreateDTO majorCreateDTO) {
        MajorEntity existing = majorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Major not found with id " + id));

        ModelMapperUtil.map(majorCreateDTO, existing);
        return ModelMapperUtil.map(majorRepository.save(existing), MajorDTO.class);
    }

    @Override
    public void deleteMajor(UUID id) {
        majorRepository.deleteById(id);
    }

    @Override
    public List<MajorDTO> searchMajors(String keyword) {
        return ModelMapperUtil.mapAll(
                majorRepository.findByNameContainingIgnoreCase(keyword),
                MajorDTO.class
        );
    }
}
