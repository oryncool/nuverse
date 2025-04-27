package kz.tech.nuverse;

import kz.tech.nuverse.controller.SchoolController;
import kz.tech.nuverse.model.dto.SchoolDTO;
import kz.tech.nuverse.model.dto.create.SchoolCreateDTO;
import kz.tech.nuverse.service.SchoolService;
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

class SchoolControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SchoolService schoolService;

    @InjectMocks
    private SchoolController schoolController;

    private SchoolDTO schoolDTO;
    private SchoolCreateDTO schoolCreateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(schoolController).build();

        schoolDTO = new SchoolDTO();
        schoolDTO.setId(UUID.randomUUID());
        schoolDTO.setName("School of Engineering");

        schoolCreateDTO = new SchoolCreateDTO();
        schoolCreateDTO.setId(UUID.randomUUID());
        schoolCreateDTO.setName("School of Engineering");

    }

    @Test
    void testCreateSchool() throws Exception {
        when(schoolService.createSchool(any())).thenReturn(schoolDTO);

        mockMvc.perform(post("/api/schools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + schoolCreateDTO.getId() + "\",\"name\":\"School of Engineering\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("School of Engineering"));
    }

    @Test
    void testGetSchoolById() throws Exception {
        when(schoolService.getSchoolById(any(UUID.class))).thenReturn(schoolDTO);

        mockMvc.perform(get("/api/schools/{id}", schoolDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("School of Engineering"));
    }

    @Test
    void testGetAllSchools() throws Exception {
        when(schoolService.getAllSchools()).thenReturn(Collections.singletonList(schoolDTO));

        mockMvc.perform(get("/api/schools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("School of Engineering"));
    }

    @Test
    void testUpdateSchool() throws Exception {
        when(schoolService.updateSchool(any(UUID.class), any())).thenReturn(schoolDTO);

        mockMvc.perform(put("/api/schools/{id}", schoolDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + schoolCreateDTO.getId() + "\",\"name\":\"School of Engineering\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("School of Engineering"));
    }

    @Test
    void testDeleteSchool() throws Exception {
        doNothing().when(schoolService).deleteSchool(any(UUID.class));

        mockMvc.perform(delete("/api/schools/{id}", schoolDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchSchools() throws Exception {
        when(schoolService.searchSchools(anyString())).thenReturn(Collections.singletonList(schoolDTO));

        mockMvc.perform(get("/api/schools/search")
                        .param("keyword", "Engineering"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("School of Engineering"));
    }
}
