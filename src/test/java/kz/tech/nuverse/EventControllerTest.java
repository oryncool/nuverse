package kz.tech.nuverse;

import jakarta.servlet.http.HttpServletRequest;
import kz.tech.nuverse.controller.EventController;
import kz.tech.nuverse.model.dto.EventDTO;
import kz.tech.nuverse.model.dto.create.EventCreateDTO;
import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import kz.tech.nuverse.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private EventController eventController;

    private EventDTO eventDTO;
    private EventCreateDTO eventCreateDTO;
    private UUID eventId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        eventId = UUID.randomUUID();

        BaseDictionaryDTO eventType = new BaseDictionaryDTO();
        eventType.setId(1L);
        eventType.setValueEn("Conference");

        eventCreateDTO = EventCreateDTO.builder()
                .type(eventType)
                .name("Tech Event")
                .location("Main Hall")
                .eventDatetime(LocalDateTime.of(2025, 5, 10, 14, 30))
                .build();

        eventDTO = EventDTO.builder()
                .id(eventId)
                .type(eventType)
                .name(eventCreateDTO.getName())
                .location(eventCreateDTO.getLocation())
                .eventDatetime(eventCreateDTO.getEventDatetime())
                .build();
    }

    @Test
    void testCreateEvent() throws Exception {
        when(eventService.createEvent(any(), any(HttpServletRequest.class))).thenReturn(eventDTO);

        String content = String.format("""
                {
                    "type": { "id": "%s", "name": "Conference" },
                    "name": "Tech Event",
                    "location": "Main Hall",
                    "eventDatetime": "2025-05-10T14:30:00"
                }
                """, eventDTO.getType().getId());

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tech Event"))
                .andExpect(jsonPath("$.location").value("Main Hall"))
                .andExpect(jsonPath("$.type.valueEn").value("Conference"));
    }

    @Test
    void testGetEventById() throws Exception {
        when(eventService.getEventById(eventId)).thenReturn(eventDTO);

        mockMvc.perform(get("/api/events/{id}", eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId.toString()))
                .andExpect(jsonPath("$.name").value("Tech Event"));
    }

    @Test
    void testGetAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Collections.singletonList(eventDTO));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(eventId.toString()));
    }

    @Test
    void testUpdateEvent() throws Exception {
        when(eventService.updateEvent(eq(eventId), any())).thenReturn(eventDTO);

        String content = String.format("""
                {
                    "type": { "id": "%s", "valueEn": "Conference" },
                    "name": "Tech Event",
                    "location": "Main Hall",
                    "eventDatetime": "2025-05-10T14:30:00"
                }
                """, eventDTO.getType().getId());

        mockMvc.perform(put("/api/events/{id}", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId.toString()))
                .andExpect(jsonPath("$.name").value("Tech Event"));
    }

    @Test
    void testDeleteEvent() throws Exception {
        doNothing().when(eventService).deleteEvent(eventId);

        mockMvc.perform(delete("/api/events/{id}", eventId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchEvents() throws Exception {
        when(eventService.searchEvents("Tech")).thenReturn(Collections.singletonList(eventDTO));

        mockMvc.perform(get("/api/events/search")
                        .param("keyword", "Tech"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tech Event"));
    }
}
