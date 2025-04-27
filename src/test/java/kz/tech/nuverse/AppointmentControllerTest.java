package kz.tech.nuverse;

import kz.tech.nuverse.controller.AppointmentController;
import kz.tech.nuverse.controller.dictionary.WeekDictionaryController;
import kz.tech.nuverse.model.dto.AppointmentDTO;
import kz.tech.nuverse.model.dto.create.AppointmentCreateDTO;
import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import kz.tech.nuverse.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @InjectMocks
    private WeekDictionaryController weekDictionaryController;

    private AppointmentDTO appointmentDTO;
    private AppointmentCreateDTO appointmentCreateDTO;
    private UUID appointmentId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();

        appointmentId = UUID.randomUUID();

        BaseDictionaryDTO weekDTO = new BaseDictionaryDTO();
        weekDTO.setId(1L);
        weekDTO.setValueEn("Monday");
        weekDTO.setExist(true);

        appointmentCreateDTO = new AppointmentCreateDTO();
        appointmentCreateDTO.setWeek(weekDTO);
        appointmentCreateDTO.setStartTime(appointmentCreateDTO.getStartTime());
        appointmentCreateDTO.setEndTime(appointmentCreateDTO.getStartTime());

        appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointmentId);
        appointmentDTO.setWeek(weekDTO);
        appointmentDTO.setStartTime(LocalTime.of(9, 0));
        appointmentDTO.setEndTime(LocalTime.of(10, 0));
    }

    @Test
    void testCreateAppointment() throws Exception {
        when(appointmentService.createAppointment(any())).thenReturn(appointmentDTO);

        String requestBody = String.format("""
                {
                  "week": { "id": "%s", "valueEn": "Monday" },
                  "startTime": "09:00:00",
                  "endTime": "10:00:00"
                }
                """, appointmentDTO.getWeek().getId());

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(appointmentId.toString()));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(appointmentDTO);

        mockMvc.perform(get("/api/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentId.toString()))
                .andExpect(jsonPath("$.week.valueEn").value("Monday"));
    }

    @Test
    void testGetAllAppointments() throws Exception {
        when(appointmentService.getAllAppointments()).thenReturn(Collections.singletonList(appointmentDTO));

        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(appointmentId.toString()));
    }

    @Test
    void testUpdateAppointment() throws Exception {
        when(appointmentService.updateAppointment(eq(appointmentId), any())).thenReturn(appointmentDTO);

        String requestBody = String.format("""
                {
                  "week": { "id": "%s", "name": "Monday" },
                  "startTime": "09:00:00",
                  "endTime": "10:00:00"
                }
                """, appointmentDTO.getWeek().getId());

        mockMvc.perform(put("/api/appointments/{id}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentId.toString()))
                .andExpect(jsonPath("$.week.valueEn").value("Monday"));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        doNothing().when(appointmentService).deleteAppointment(appointmentId);

        mockMvc.perform(delete("/api/appointments/{id}", appointmentId))
                .andExpect(status().isNoContent());
    }
}
