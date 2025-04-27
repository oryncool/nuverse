package kz.tech.nuverse;

import kz.tech.nuverse.controller.MajorController;
import kz.tech.nuverse.model.dto.MajorDTO;
import kz.tech.nuverse.model.dto.SchoolDTO;
import kz.tech.nuverse.model.dto.create.MajorCreateDTO;
import kz.tech.nuverse.service.MajorService;
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

class MajorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MajorService majorService;

    @InjectMocks
    private MajorController majorController;

    private MajorDTO majorDTO;
    private MajorCreateDTO majorCreateDTO;
    private UUID majorId;
    private UUID schoolId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(majorController).build();

        majorId = UUID.randomUUID();
        schoolId = UUID.randomUUID();

        SchoolDTO schoolDTO = SchoolDTO.builder()
                .id(schoolId)
                .name("Engineering School")
                .build();

        majorCreateDTO = MajorCreateDTO.builder()
                .name("Computer Science")
                .schoolId(schoolId)
                .build();

        majorDTO = MajorDTO.builder()
                .id(majorId)
                .name(majorCreateDTO.getName())
                .school(schoolDTO)
                .build();
    }

    @Test
    void testCreateMajor() throws Exception {
        when(majorService.createMajor(any())).thenReturn(majorDTO);

        String content = String.format("""
                {
                    "name": "Computer Science",
                    "schoolId": "%s"
                }
                """, schoolId);

        mockMvc.perform(post("/api/majors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Computer Science"))
                .andExpect(jsonPath("$.school.name").value("Engineering School"));
    }

    @Test
    void testGetMajorById() throws Exception {
        when(majorService.getMajorById(majorId)).thenReturn(majorDTO);

        mockMvc.perform(get("/api/majors/{id}", majorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(majorId.toString()))
                .andExpect(jsonPath("$.name").value("Computer Science"));
    }

    @Test
    void testGetAllMajors() throws Exception {
        when(majorService.getAllMajors()).thenReturn(Collections.singletonList(majorDTO));

        mockMvc.perform(get("/api/majors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(majorId.toString()));
    }

    @Test
    void testUpdateMajor() throws Exception {
        when(majorService.updateMajor(eq(majorId), any())).thenReturn(majorDTO);

        String content = String.format("""
                {
                    "name": "Computer Science",
                    "schoolId": "%s"
                }
                """, schoolId);

        mockMvc.perform(put("/api/majors/{id}", majorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Computer Science"));
    }

    @Test
    void testDeleteMajor() throws Exception {
        doNothing().when(majorService).deleteMajor(majorId);

        mockMvc.perform(delete("/api/majors/{id}", majorId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchMajors() throws Exception {
        when(majorService.searchMajors("Comp")).thenReturn(Collections.singletonList(majorDTO));

        mockMvc.perform(get("/api/majors/search")
                        .param("keyword", "Comp"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Computer Science"));
    }
}
