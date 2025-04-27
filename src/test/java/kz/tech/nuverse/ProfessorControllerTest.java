package kz.tech.nuverse;

import kz.tech.nuverse.controller.ProfessorController;
import kz.tech.nuverse.model.dto.ProfessorDTO;
import kz.tech.nuverse.model.dto.UserDTO;
import kz.tech.nuverse.model.dto.create.ProfessorCreateDTO;
import kz.tech.nuverse.service.ProfessorService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfessorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private ProfessorController professorController;

    private ProfessorDTO professorDTO;
    private ProfessorCreateDTO professorCreateDTO;
    private UUID professorId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(professorController).build();

        professorId = UUID.randomUUID();
        userId = UUID.randomUUID();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("johndoe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setLastName("Smith");

        professorDTO = new ProfessorDTO();
        professorDTO.setId(professorId);
        professorDTO.setUser(userDTO);
        professorDTO.setOffice("Office 101");
        professorDTO.setResearchInterest("AI");

        professorCreateDTO = new ProfessorCreateDTO();
        professorCreateDTO.setUserId(userId);
        professorCreateDTO.setOffice("Office 101");
        professorCreateDTO.setResearchInterest("AI");

    }

    @Test
    void testCreateProfessor() throws Exception {
        when(professorService.createProfessor(any())).thenReturn(professorDTO);

        String content = String.format("""
                {
                    "userId": "%s",
                    "office": "Office 101",
                    "researchInterest": "AI"
                }
                """, userId);

        mockMvc.perform(post("/api/professors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.office").value("Office 101"))
                .andExpect(jsonPath("$.researchInterest").value("AI"))
                .andExpect(jsonPath("$.user.name").value("John"))
                .andExpect(jsonPath("$.user.surname").value("Doe"))
                .andExpect(jsonPath("$.user.lastName").value("Smith"))
                .andExpect(jsonPath("$.user.email").value("john.doe@example.com"));
    }

    @Test
    void testGetProfessorById() throws Exception {
        when(professorService.getProfessorById(professorId)).thenReturn(professorDTO);

        mockMvc.perform(get("/api/professors/{id}", professorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.office").value("Office 101"))
                .andExpect(jsonPath("$.researchInterest").value("AI"));
    }

    @Test
    void testGetAllProfessors() throws Exception {
        when(professorService.getAllProfessors()).thenReturn(Collections.singletonList(professorDTO));

        mockMvc.perform(get("/api/professors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].office").value("Office 101"))
                .andExpect(jsonPath("$[0].user.username").value("johndoe"));
    }

    @Test
    void testUpdateProfessor() throws Exception {
        when(professorService.updateProfessor(eq(professorId), any())).thenReturn(professorDTO);

        String content = String.format("""
                {
                    "userId": "%s",
                    "office": "Updated Office",
                    "researchInterest": "Updated Research"
                }
                """, userId);

        mockMvc.perform(put("/api/professors/{id}", professorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.office").value("Office 101"))
                .andExpect(jsonPath("$.researchInterest").value("AI"));
    }

    @Test
    void testDeleteProfessor() throws Exception {
        doNothing().when(professorService).deleteProfessor(professorId);

        mockMvc.perform(delete("/api/professors/{id}", professorId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchProfessors() throws Exception {
        when(professorService.searchProfessors("AI")).thenReturn(Collections.singletonList(professorDTO));

        mockMvc.perform(get("/api/professors/search")
                        .param("keyword", "AI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].researchInterest").value("AI"))
                .andExpect(jsonPath("$[0].user.name").value("John"));
    }
}
