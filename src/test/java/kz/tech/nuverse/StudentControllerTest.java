package kz.tech.nuverse;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tech.nuverse.controller.StudentController;
import kz.tech.nuverse.model.dto.*;
import kz.tech.nuverse.model.dto.create.MajorCreateDTO;
import kz.tech.nuverse.model.dto.create.StudentCreateDTO;
import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import kz.tech.nuverse.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private StudentDTO studentDTO;
    private StudentCreateDTO studentCreateDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper = new ObjectMapper();

        SchoolDTO schoolDTO = SchoolDTO.builder()
                .id(UUID.randomUUID())
                .name("Engineering")
                .build();

        MajorDTO majorDTO = MajorDTO.builder()
                .id(UUID.randomUUID())
                .name("Computer Science")
                .school(schoolDTO)
                .build();

        BaseDictionaryDTO degree = new BaseDictionaryDTO();
        degree.setId(1L);
        degree.setValueEn("Bachelor");
        degree.setExist(true);

        UserDTO userDTO = UserDTO.builder()
                .id(UUID.randomUUID())
                .name("John")
                .surname("Doe")
                .lastName("Smith")
                .birthday(LocalDate.of(2000, 1, 1))
                .email("john.doe@example.com")
                .username("johndoe")
                .role(degree)
                .build();

        studentDTO = StudentDTO.builder()
                .id(UUID.randomUUID())
                .user(userDTO)
                .enrollmentYear(2020)
                .gpa(3.8)
                .major(majorDTO)
                .degree(degree)
                .build();

        studentCreateDTO = StudentCreateDTO.builder()
                .id(UUID.randomUUID())
                .userId(userDTO.getId())
                .enrollmentYear(2020)
                .gpa(3.8)
                .major(MajorCreateDTO.builder()
                        .name("Computer Science")
                        .schoolId(schoolDTO.getId())
                        .build())
                .degree(degree)
                .build();
    }

    @Test
    void testCreateStudent() throws Exception {
        when(studentService.createStudent(any())).thenReturn(studentDTO);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.name").value("John"))
                .andExpect(jsonPath("$.gpa").value(3.8));
    }

    @Test
    void testGetStudentById() throws Exception {
        when(studentService.getStudentById(any(UUID.class))).thenReturn(studentDTO);

        mockMvc.perform(get("/api/students/{id}", studentDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name").value("John"));
    }

    @Test
    void testGetAllStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(studentDTO));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.name").value("John"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        when(studentService.updateStudent(any(UUID.class), any())).thenReturn(studentDTO);

        mockMvc.perform(put("/api/students/{id}", studentDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gpa").value(3.8));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(any(UUID.class));

        mockMvc.perform(delete("/api/students/{id}", studentDTO.getId()))
                .andExpect(status().isNoContent());
    }
}
