package kz.tech.nuverse;

import kz.tech.nuverse.controller.CourseController;
import kz.tech.nuverse.model.dto.CourseDTO;
import kz.tech.nuverse.model.dto.SchoolDTO;
import kz.tech.nuverse.model.dto.create.CourseCreateDTO;
import kz.tech.nuverse.service.CourseService;
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

class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private CourseDTO courseDTO;
    private CourseCreateDTO courseCreateDTO;
    private UUID courseId;
    private UUID schoolId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();

        courseId = UUID.randomUUID();
        schoolId = UUID.randomUUID();

        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setId(schoolId);
        schoolDTO.setName("Engineering School");

        courseCreateDTO = new CourseCreateDTO();
        courseCreateDTO.setName("Data Structures");
        courseCreateDTO.setSchoolId(schoolId);

        courseDTO = new CourseDTO();
        courseDTO.setId(courseId);
        courseDTO.setName(courseCreateDTO.getName());
        courseDTO.setSchool(schoolDTO);

    }

    @Test
    void testCreateCourse() throws Exception {
        when(courseService.createCourse(any())).thenReturn(courseDTO);

        String requestBody = String.format("""
                {
                  "name": "Data Structures",
                  "schoolId": "%s"
                }
                """, schoolId);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value("Data Structures"))
                .andExpect(jsonPath("$.school.name").value("Engineering School"));
    }

    @Test
    void testGetCourseById() throws Exception {
        when(courseService.getCourseById(courseId)).thenReturn(courseDTO);

        mockMvc.perform(get("/api/courses/{id}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value("Data Structures"))
                .andExpect(jsonPath("$.school.name").value("Engineering School"));
    }

    @Test
    void testGetAllCourses() throws Exception {
        when(courseService.getAllCourses()).thenReturn(Collections.singletonList(courseDTO));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(courseId.toString()));
    }

    @Test
    void testUpdateCourse() throws Exception {
        when(courseService.updateCourse(eq(courseId), any())).thenReturn(courseDTO);

        String requestBody = String.format("""
                {
                  "name": "Data Structures",
                  "schoolId": "%s"
                }
                """, schoolId);

        mockMvc.perform(put("/api/courses/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value("Data Structures"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        doNothing().when(courseService).deleteCourse(courseId);

        mockMvc.perform(delete("/api/courses/{id}", courseId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchCourses() throws Exception {
        when(courseService.searchCourses("Data")).thenReturn(Collections.singletonList(courseDTO));

        mockMvc.perform(get("/api/courses/search")
                        .param("keyword", "Data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Data Structures"));
    }
}
