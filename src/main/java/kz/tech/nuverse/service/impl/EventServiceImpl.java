package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import kz.tech.nuverse.model.dto.EventDTO;
import kz.tech.nuverse.model.dto.create.EventCreateDTO;
import kz.tech.nuverse.model.entity.EventEntity;
import kz.tech.nuverse.model.entity.User;
import kz.tech.nuverse.repository.EventRepository;
import kz.tech.nuverse.repository.UserRepository;
import kz.tech.nuverse.security.JwtTokenProvider;
import kz.tech.nuverse.service.EventService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventDTO createEvent(EventCreateDTO eventCreateDTO, HttpServletRequest request) {
        EventEntity entity = ModelMapperUtil.map(eventCreateDTO, EventEntity.class);
        entity = eventRepository.save(entity);
        return ModelMapperUtil.map(entity, EventDTO.class);
    }

    @Override
    public EventDTO getEventById(UUID id) {
        EventEntity entity = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + id));
        return ModelMapperUtil.map(entity, EventDTO.class);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return ModelMapperUtil.mapAll(eventRepository.findAll(), EventDTO.class);
    }

    @Override
    public EventDTO updateEvent(UUID id, EventCreateDTO eventCreateDTO) {
        EventEntity existing = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + id));
        ModelMapperUtil.map(eventCreateDTO, existing);
        EventEntity updated = eventRepository.save(existing);
        return ModelMapperUtil.map(updated, EventDTO.class);
    }

    @Override
    public void deleteEvent(UUID id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventDTO> searchEvents(String keyword) {
        return ModelMapperUtil.mapAll(
                eventRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword),
                EventDTO.class
        );
    }
}
