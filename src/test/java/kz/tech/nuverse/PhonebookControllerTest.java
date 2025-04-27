package kz.tech.nuverse;

import kz.tech.nuverse.controller.PhonebookController;
import kz.tech.nuverse.model.dto.PhonebookDTO;
import kz.tech.nuverse.model.dto.UserDTO;
import kz.tech.nuverse.model.dto.create.PhonebookCreateDTO;
import kz.tech.nuverse.model.entity.User;
import kz.tech.nuverse.service.PhonebookService;
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

class PhonebookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PhonebookService phonebookService;

    @InjectMocks
    private PhonebookController phonebookController;

    private PhonebookDTO phonebookDTO;
    private PhonebookCreateDTO phonebookCreateDTO;
    private UUID phonebookId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(phonebookController).build();

        phonebookId = UUID.randomUUID();
        userId = UUID.randomUUID();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("johndoe");
        userDTO.setEmail("johndoe@example.com");

        phonebookCreateDTO = new PhonebookCreateDTO();
        phonebookCreateDTO.setUserId(userId);
        phonebookCreateDTO.setPhone("+123456789");

        phonebookDTO = new PhonebookDTO();
        phonebookDTO.setId(phonebookId);
        phonebookDTO.setUser(userDTO);
        phonebookDTO.setPhone(phonebookCreateDTO.getPhone());

    }

    @Test
    void testCreatePhonebook() throws Exception {
        when(phonebookService.createPhonebook(any())).thenReturn(phonebookDTO);

        String content = String.format("""
                {
                    "userId": "%s",
                    "phone": "+123456789"
                }
                """, userId);

        mockMvc.perform(post("/api/phonebooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.phone").value("+123456789"))
                .andExpect(jsonPath("$.user.username").value("johndoe"));
    }

    @Test
    void testGetPhonebookById() throws Exception {
        when(phonebookService.getPhonebookById(phonebookId)).thenReturn(phonebookDTO);

        mockMvc.perform(get("/api/phonebooks/{id}", phonebookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(phonebookId.toString()))
                .andExpect(jsonPath("$.phone").value("+123456789"));
    }

    @Test
    void testGetAllPhonebooks() throws Exception {
        when(phonebookService.getAllPhonebooks()).thenReturn(Collections.singletonList(phonebookDTO));

        mockMvc.perform(get("/api/phonebooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(phonebookId.toString()));
    }

    @Test
    void testUpdatePhonebook() throws Exception {
        when(phonebookService.updatePhonebook(eq(phonebookId), any())).thenReturn(phonebookDTO);

        String content = String.format("""
                {
                    "userId": "%s",
                    "phone": "+123456789"
                }
                """, userId);

        mockMvc.perform(put("/api/phonebooks/{id}", phonebookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("+123456789"));
    }

    @Test
    void testDeletePhonebook() throws Exception {
        doNothing().when(phonebookService).deletePhonebook(phonebookId);

        mockMvc.perform(delete("/api/phonebooks/{id}", phonebookId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchPhonebooks() throws Exception {
        when(phonebookService.searchPhonebooks("john")).thenReturn(Collections.singletonList(phonebookDTO));

        mockMvc.perform(get("/api/phonebooks/search")
                        .param("keyword", "john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.username").value("johndoe"))
                .andExpect(jsonPath("$[0].phone").value("+123456789"));
    }
}
