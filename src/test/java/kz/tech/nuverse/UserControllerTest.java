package kz.tech.nuverse;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tech.nuverse.controller.UserController;
import kz.tech.nuverse.model.dto.UserDTO;
import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import kz.tech.nuverse.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

        // Manually instantiate BaseDictionaryDTO without using the builder
        BaseDictionaryDTO role = new BaseDictionaryDTO();
        role.setId(1L);
        role.setValueEn("Admin");
        role.setValueRu("Админ");
        role.setValueKz("Әкім");
        role.setExist(true);

        userDTO = UserDTO.builder()
                .id(UUID.randomUUID())
                .role(role)  // Use the manually instantiated role
                .name("John")
                .surname("Doe")
                .lastName("Smith")
                .birthday(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .username("john_doe")
                .build();
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(any(UUID.class))).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/{id}", userDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.getId().toString()))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.role.valueEn").value("Admin"))
                .andExpect(jsonPath("$.role.valueRu").value("Админ"))
                .andExpect(jsonPath("$.role.valueKz").value("Әкім"))
                .andExpect(jsonPath("$.role.exist").value(true));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDTO));

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"))
                .andExpect(jsonPath("$[0].role.valueEn").value("Admin"))
                .andExpect(jsonPath("$[0].role.valueRu").value("Админ"));
    }

    @Test
    void testSearchUsers() throws Exception {
        when(userService.searchUsers(anyString())).thenReturn(Collections.singletonList(userDTO));

        mockMvc.perform(get("/api/users/search")
                        .param("keyword", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"))
                .andExpect(jsonPath("$[0].role.valueEn").value("Admin"));
    }
}
