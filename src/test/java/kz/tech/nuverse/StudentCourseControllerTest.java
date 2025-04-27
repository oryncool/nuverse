package kz.tech.nuverse;

import kz.tech.nuverse.controller.StudentCourseController;
import kz.tech.nuverse.model.dto.StudentCourseDTO;
import kz.tech.nuverse.model.dto.create.StudentCourseCreateDTO;
import kz.tech.nuverse.model.dto.StudentDTO;
import kz.tech.nuverse.model.dto.CourseDTO;
import kz.tech.nuverse.model.dto.SchoolDTO;
import kz.tech.nuverse.service.StudentCourseService;
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

class StudentCourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentCourseService service;

    @InjectMocks
    private StudentCourseController controller;

    private StudentCourseDTO dto;
    private StudentCourseCreateDTO createDTO;

    private UUID studentId;
    private UUID courseId;
    private UUID studentCourseId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        studentCourseId = UUID.randomUUID();

        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setId(UUID.randomUUID());
        schoolDTO.setName("School of Engineering");

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(courseId);
        courseDTO.setName("Computer Science 101");
        courseDTO.setSchool(schoolDTO);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentId);
        studentDTO.setEnrollmentYear(2021);
        studentDTO.setGpa(3.75);

        dto = new StudentCourseDTO();
        dto.setId(studentCourseId);
        dto.setStudent(studentDTO);
        dto.setCourse(courseDTO);

        createDTO = new StudentCourseCreateDTO();
        createDTO.setStudentId(studentId);
        createDTO.setCourseId(courseId);

    }

    @Test
    void testCreate() throws Exception {
        when(service.create(any())).thenReturn(dto);

        String content = String.format("""
                {
                    "studentId": "%s",
                    "courseId": "%s"
                }
                """, studentId, courseId);

        mockMvc.perform(post("/api/student-courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(studentCourseId.toString()))
                .andExpect(jsonPath("$.student.id").value(studentId.toString()))
                .andExpect(jsonPath("$.course.name").value("Computer Science 101"));
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById(studentCourseId)).thenReturn(dto);

        mockMvc.perform(get("/api/student-courses/{id}", studentCourseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentCourseId.toString()));
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/student-courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(studentCourseId.toString()));
    }

    @Test
    void testUpdate() throws Exception {
        when(service.update(eq(studentCourseId), any())).thenReturn(dto);

        String content = String.format("""
                {
                    "studentId": "%s",
                    "courseId": "%s"
                }
                """, studentId, courseId);

        mockMvc.perform(put("/api/student-courses/{id}", studentCourseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentCourseId.toString()));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(studentCourseId);

        mockMvc.perform(delete("/api/student-courses/{id}", studentCourseId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByStudentId() throws Exception {
        when(service.findByStudentId(studentId)).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/student-courses/student/{studentId}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].student.id").value(studentId.toString()));
    }
}
