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

        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setId(UUID.randomUUID());
        schoolDTO.setName("Engineering");

        MajorDTO majorDTO = new MajorDTO();
        majorDTO.setId(UUID.randomUUID());
        majorDTO.setName("Computer Science");
        majorDTO.setSchool(schoolDTO);

        BaseDictionaryDTO degree = new BaseDictionaryDTO();
        degree.setId(1L);
        degree.setValueEn("Bachelor");
        degree.setExist(true);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(UUID.randomUUID());
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setLastName("Smith");
        userDTO.setBirthday(LocalDate.of(2000, 1, 1));
        userDTO.setEmail("john.doe@example.com");
        userDTO.setUsername("johndoe");
        userDTO.setRole(degree);

        studentDTO = new StudentDTO();
        studentDTO.setId(UUID.randomUUID());
        studentDTO.setUser(userDTO);
        studentDTO.setEnrollmentYear(2020);
        studentDTO.setGpa(3.8);
        studentDTO.setMajor(majorDTO);
        studentDTO.setDegree(degree);

        studentCreateDTO = new StudentCreateDTO();
        studentCreateDTO.setId(UUID.randomUUID());
        studentCreateDTO.setUserId(userDTO.getId());
        studentCreateDTO.setEnrollmentYear(2020);
        studentCreateDTO.setGpa(3.8);
        studentCreateDTO.setMajor(new MajorCreateDTO());
        studentCreateDTO.getMajor().setName("Computer Science");
        studentCreateDTO.getMajor().setSchoolId(schoolDTO.getId());
        studentCreateDTO.setDegree(degree);

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
