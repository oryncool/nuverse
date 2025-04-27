package kz.tech.nuverse.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.tech.nuverse.model.dto.NewsDTO;
import kz.tech.nuverse.model.dto.create.NewsCreateDTO;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewsDTO createNews(NewsCreateDTO newsCreateDTO, HttpServletRequest request);
    NewsDTO getNewsById(UUID id);
    List<NewsDTO> getAllNews();
    NewsDTO updateNews(UUID id, NewsCreateDTO newsCreateDTO);
    void deleteNews(UUID id);
    List<NewsDTO> searchNews(String keyword);

}

