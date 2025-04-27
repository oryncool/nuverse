package kz.tech.nuverse;

import kz.tech.nuverse.controller.NewsController;
import kz.tech.nuverse.model.dto.NewsDTO;
import kz.tech.nuverse.model.dto.create.NewsCreateDTO;
import kz.tech.nuverse.service.NewsService;
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

class NewsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NewsService newsService;

    @InjectMocks
    private NewsController newsController;

    private NewsDTO newsDTO;
    private NewsCreateDTO newsCreateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
        newsDTO = new NewsDTO();
        newsDTO.setId(UUID.randomUUID());
        newsDTO.setType(null);
        newsDTO.setUser(null);
        newsDTO.setHeader("Header");
        newsDTO.setText("Text");

        newsCreateDTO = new NewsCreateDTO();
        newsCreateDTO.setType(null);
        newsCreateDTO.setHeader("Header");
        newsCreateDTO.setText("Text");

    }

    @Test
    void testCreateNews() throws Exception {
        when(newsService.createNews(any(), any())).thenReturn(newsDTO);

        mockMvc.perform(post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":{},\"header\":\"Header\",\"text\":\"Text\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.header").value("Header"))
                .andExpect(jsonPath("$.text").value("Text"));
    }

    @Test
    void testGetNewsById() throws Exception {
        when(newsService.getNewsById(any(UUID.class))).thenReturn(newsDTO);

        mockMvc.perform(get("/api/news/{id}", newsDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header").value("Header"))
                .andExpect(jsonPath("$.text").value("Text"));
    }

    @Test
    void testGetAllNews() throws Exception {
        when(newsService.getAllNews()).thenReturn(Collections.singletonList(newsDTO));

        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].header").value("Header"))
                .andExpect(jsonPath("$[0].text").value("Text"));
    }

    @Test
    void testUpdateNews() throws Exception {
        when(newsService.updateNews(any(UUID.class), any())).thenReturn(newsDTO);

        mockMvc.perform(put("/api/news/{id}", newsDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":null,\"header\":\"Updated Header\",\"text\":\"Updated Text\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header").value("Header"))
                .andExpect(jsonPath("$.text").value("Text"));
    }

    @Test
    void testDeleteNews() throws Exception {
        doNothing().when(newsService).deleteNews(any(UUID.class));

        mockMvc.perform(delete("/api/news/{id}", newsDTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchNews() throws Exception {
        when(newsService.searchNews(anyString())).thenReturn(Collections.singletonList(newsDTO));

        mockMvc.perform(get("/api/news/search")
                        .param("keyword", "Header"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].header").value("Header"))
                .andExpect(jsonPath("$[0].text").value("Text"));
    }
}
