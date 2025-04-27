package kz.tech.nuverse;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tech.nuverse.controller.UniversityController;
import kz.tech.nuverse.model.dto.UniversityDTO;
import kz.tech.nuverse.model.dto.create.UniversityCreateDTO;
import kz.tech.nuverse.service.UniversityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UniversityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UniversityService universityService;

    @InjectMocks
    private UniversityController universityController;

    private UniversityDTO universityDTO;
    private UniversityCreateDTO universityCreateDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(universityController).build();
        objectMapper = new ObjectMapper();

        universityDTO = UniversityDTO.builder()
                .id(UUID.randomUUID())
                .name("Tech University")
                .country("Kazakhstan")
                .build();

        universityCreateDTO = UniversityCreateDTO.builder()
                .id(UUID.randomUUID())
                .name("Tech University")
                .country("Kazakhstan")
                .build();
    }

    @Test
    void testCreateUniversity() throws Exception {
        when(universityService.createUniversity(any())).thenReturn(universityDTO);

        mockMvc.perform(post("/api/universities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(universityCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tech University"))
                .andExpect(jsonPath("$.country").value("Kazakhstan"));
    }

    @Test
    void testGetUniversityById() throws Exception {
        when(universityService.getUniversityById(any(UUID.class))).thenReturn(universityDTO);

        mockMvc.perform(get("/api/universities/{id}", universityDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tech University"))
                .andExpect(jsonPath("$.country").value("Kazakhstan"));
    }

    @Test
    void testGetAllUniversities() throws Exception {
        when(universityService.getAllUniversities()).thenReturn(Collections.singletonList(universityDTO));

        mockMvc.perform(get("/api/universities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tech University"))
                .andExpect(jsonPath("$[0].country").value("Kazakhstan"));
    }

    @Test
    void testUpdateUniversity() throws Exception {
        when(universityService.updateUniversity(any(UUID.class), any())).thenReturn(universityDTO);

        mockMvc.perform(put("/api/universities/{id}", universityDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(universityCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tech University"))
                .andExpect(jsonPath("$.country").value("Kazakhstan"));
    }

    @Test
    void testDeleteUniversity() throws Exception {
        doNothing().when(universityService).deleteUniversity(any(UUID.class));

        mockMvc.perform(delete("/api/universities/{id}", universityDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchUniversities() throws Exception {
        when(universityService.searchUniversities(any())).thenReturn(Collections.singletonList(universityDTO));

        mockMvc.perform(get("/api/universities/search")
                        .param("keyword", "Tech"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tech University"))
                .andExpect(jsonPath("$[0].country").value("Kazakhstan"));
    }
}
