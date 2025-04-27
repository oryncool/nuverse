package kz.tech.nuverse.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.tech.nuverse.model.dto.EventDTO;
import kz.tech.nuverse.model.dto.create.EventCreateDTO;

import java.util.List;
import java.util.UUID;

public interface EventService {
    EventDTO createEvent(EventCreateDTO eventCreateDTO, HttpServletRequest request);
    EventDTO getEventById(UUID id);
    List<EventDTO> getAllEvents();
    EventDTO updateEvent(UUID id, EventCreateDTO eventCreateDTO);
    void deleteEvent(UUID id);
    List<EventDTO> searchEvents(String keyword);
}
