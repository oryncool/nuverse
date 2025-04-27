package kz.tech.nuverse.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kz.tech.nuverse.model.dto.NewsDTO;
import kz.tech.nuverse.model.dto.create.NewsCreateDTO;
import kz.tech.nuverse.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody @Valid NewsCreateDTO newsCreateDTO, HttpServletRequest request) {
        NewsDTO created = newsService.createNews(newsCreateDTO, request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable UUID id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(
            @PathVariable UUID id,
            @RequestBody @Valid NewsCreateDTO newsCreateDTO) {
        NewsDTO updated = newsService.updateNews(id, newsCreateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable UUID id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDTO>> searchNews(@RequestParam String keyword) {
        return ResponseEntity.ok(newsService.searchNews(keyword));
    }
}
