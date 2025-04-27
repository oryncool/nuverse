package kz.tech.nuverse;

import kz.tech.nuverse.controller.ProfessorAppointmentController;
import kz.tech.nuverse.model.dto.AppointmentDTO;
import kz.tech.nuverse.model.dto.ProfessorAppointmentDTO;
import kz.tech.nuverse.model.dto.ProfessorDTO;
import kz.tech.nuverse.model.dto.UserDTO;
import kz.tech.nuverse.model.dto.create.ProfessorAppointmentCreateDTO;
import kz.tech.nuverse.service.ProfessorAppointmentService;
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

class ProfessorAppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfessorAppointmentService service;

    @InjectMocks
    private ProfessorAppointmentController controller;

    private ProfessorAppointmentDTO dto;
    private ProfessorAppointmentCreateDTO createDTO;

    private UUID professorId;
    private UUID appointmentId;
    private UUID professorAppointmentId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        professorId = UUID.randomUUID();
        appointmentId = UUID.randomUUID();
        professorAppointmentId = UUID.randomUUID();
        userId = UUID.randomUUID();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("johndoe");
        userDTO.setEmail("johndoe@example.com");

        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professorId);
        professorDTO.setUser(userDTO);
        professorDTO.setOffice("office");
        professorDTO.setResearchInterest("interest");

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointmentId);
        appointmentDTO.setStartTime(java.time.LocalTime.of(10, 0));
        appointmentDTO.setEndTime(java.time.LocalTime.of(11, 0));

        dto = new ProfessorAppointmentDTO();
        dto.setId(professorAppointmentId);
        dto.setProfessor(professorDTO);
        dto.setAppointment(appointmentDTO);

        createDTO = new ProfessorAppointmentCreateDTO();
        createDTO.setProfessorId(professorId);
        createDTO.setAppointmentId(appointmentId);

    }

    @Test
    void testCreate() throws Exception {
        when(service.create(any())).thenReturn(dto);

        String content = String.format("""
                {
                    "professorId": "%s",
                    "appointmentId": "%s"
                }
                """, professorId, appointmentId);

        mockMvc.perform(post("/api/professor-appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(professorAppointmentId.toString()))
                .andExpect(jsonPath("$.professor.office").value("office"));
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById(professorAppointmentId)).thenReturn(dto);

        mockMvc.perform(get("/api/professor-appointments/{id}", professorAppointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(professorAppointmentId.toString()));
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/professor-appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(professorAppointmentId.toString()));
    }

    @Test
    void testUpdate() throws Exception {
        when(service.update(eq(professorAppointmentId), any())).thenReturn(dto);

        String content = String.format("""
                {
                    "professorId": "%s",
                    "appointmentId": "%s"
                }
                """, professorId, appointmentId);

        mockMvc.perform(put("/api/professor-appointments/{id}", professorAppointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(professorAppointmentId.toString()));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(professorAppointmentId);

        mockMvc.perform(delete("/api/professor-appointments/{id}", professorAppointmentId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByProfessorId() throws Exception {
        when(service.findByProfessorId(professorId)).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/professor-appointments/professor/{professorId}", professorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].professor.office").value("office"));
    }
}
