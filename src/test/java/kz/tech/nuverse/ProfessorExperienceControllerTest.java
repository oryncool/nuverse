package kz.tech.nuverse;

import kz.tech.nuverse.controller.ProfessorExperienceController;
import kz.tech.nuverse.model.dto.ProfessorExperienceDTO;
import kz.tech.nuverse.model.dto.ProfessorDTO;
import kz.tech.nuverse.model.dto.UniversityDTO;
import kz.tech.nuverse.model.dto.create.ProfessorExperienceCreateDTO;
import kz.tech.nuverse.service.ProfessorExperienceService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfessorExperienceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfessorExperienceService service;

    @InjectMocks
    private ProfessorExperienceController controller;

    private ProfessorExperienceDTO dto;
    private ProfessorExperienceCreateDTO createDTO;

    private UUID professorId;
    private UUID universityId;
    private UUID professorExperienceId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        professorId = UUID.randomUUID();
        universityId = UUID.randomUUID();
        professorExperienceId = UUID.randomUUID();

        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professorId);

        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setId(universityId);
        universityDTO.setName("University Name");
        universityDTO.setCountry("Country");

        dto = new ProfessorExperienceDTO();
        dto.setId(professorExperienceId);
        dto.setProfessor(professorDTO);
        dto.setUniversity(universityDTO);
        dto.setPosition("Associate Professor");
        dto.setStartYear(2015);
        dto.setEndYear(2020);

        createDTO = new ProfessorExperienceCreateDTO();
        createDTO.setProfessorId(professorId);
        createDTO.setUniversityId(universityId);
        createDTO.setPosition("Associate Professor");
        createDTO.setStartYear(2015);
        createDTO.setEndYear(2020);

    }

    @Test
    void testCreate() throws Exception {
        when(service.create(any())).thenReturn(dto);

        String content = String.format("""
                {
                    "professorId": "%s",
                    "universityId": "%s",
                    "position": "Associate Professor",
                    "startYear": 2015,
                    "endYear": 2020
                }
                """, professorId, universityId);

        mockMvc.perform(post("/api/professor-experiences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(professorExperienceId.toString()))
                .andExpect(jsonPath("$.position").value("Associate Professor"));
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById(professorExperienceId)).thenReturn(dto);

        mockMvc.perform(get("/api/professor-experiences/{id}", professorExperienceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(professorExperienceId.toString()));
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/professor-experiences"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(professorExperienceId.toString()));
    }

    @Test
    void testUpdate() throws Exception {
        when(service.update(eq(professorExperienceId), any())).thenReturn(dto);

        String content = String.format("""
                {
                    "professorId": "%s",
                    "universityId": "%s",
                    "position": "Associate Professor",
                    "startYear": 2015,
                    "endYear": 2020
                }
                """, professorId, universityId);

        mockMvc.perform(put("/api/professor-experiences/{id}", professorExperienceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(professorExperienceId.toString()));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(professorExperienceId);

        mockMvc.perform(delete("/api/professor-experiences/{id}", professorExperienceId))
                .andExpect(status().isNoContent());
    }
}
