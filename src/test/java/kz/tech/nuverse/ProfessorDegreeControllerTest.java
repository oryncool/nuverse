package kz.tech.nuverse;

import kz.tech.nuverse.controller.ProfessorDegreeController;
import kz.tech.nuverse.model.dto.ProfessorDegreeDTO;
import kz.tech.nuverse.model.dto.ProfessorDTO;
import kz.tech.nuverse.model.dto.UniversityDTO;
import kz.tech.nuverse.model.dto.create.ProfessorDegreeCreateDTO;
import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import kz.tech.nuverse.service.ProfessorDegreeService;
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

class ProfessorDegreeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfessorDegreeService service;

    @InjectMocks
    private ProfessorDegreeController controller;

    private ProfessorDegreeDTO dto;
    private ProfessorDegreeCreateDTO createDTO;

    private UUID professorId;
    private UUID universityId;
    private UUID professorDegreeId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        professorId = UUID.randomUUID();
        universityId = UUID.randomUUID();
        professorDegreeId = UUID.randomUUID();

        BaseDictionaryDTO degree = new BaseDictionaryDTO();
        degree.setId(1L);
        degree.setValueEn("PhD");

        ProfessorDTO professorDTO = ProfessorDTO.builder()
                .id(professorId)
                .build();

        UniversityDTO universityDTO = UniversityDTO.builder()
                .id(universityId)
                .name("University Name")
                .country("Country")
                .build();

        dto = ProfessorDegreeDTO.builder()
                .id(professorDegreeId)
                .professor(professorDTO)
                .university(universityDTO)
                .degree(degree)
                .startYear(2010)
                .endYear(2015)
                .build();

        createDTO = ProfessorDegreeCreateDTO.builder()
                .professorId(professorId)
                .universityId(universityId)
                .degree(degree)
                .startYear(2010)
                .endYear(2015)
                .build();
    }

    @Test
    void testCreate() throws Exception {
        when(service.create(any())).thenReturn(dto);

        String content = String.format("""
                {
                    "professorId": "%s",
                    "universityId": "%s",
                    "degree": {"id": 1, "valueEn": "PhD"},
                    "startYear": 2010,
                    "endYear": 2015
                }
                """, professorId, universityId);

        mockMvc.perform(post("/api/professor-degrees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(professorDegreeId.toString()))
                .andExpect(jsonPath("$.professor.id").value(professorId.toString()))
                .andExpect(jsonPath("$.university.name").value("University Name"));
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById(professorDegreeId)).thenReturn(dto);

        mockMvc.perform(get("/api/professor-degrees/{id}", professorDegreeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(professorDegreeId.toString()));
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/professor-degrees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(professorDegreeId.toString()));
    }

    @Test
    void testUpdate() throws Exception {
        when(service.update(eq(professorDegreeId), any())).thenReturn(dto);

        String content = String.format("""
                {
                    "professorId": "%s",
                    "universityId": "%s",
                    "degree": {"id": 1, "valueEn": "PhD"},
                    "startYear": 2010,
                    "endYear": 2015
                }
                """, professorId, universityId);

        mockMvc.perform(put("/api/professor-degrees/{id}", professorDegreeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(professorDegreeId.toString()));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(professorDegreeId);

        mockMvc.perform(delete("/api/professor-degrees/{id}", professorDegreeId))
                .andExpect(status().isNoContent());
    }
}
