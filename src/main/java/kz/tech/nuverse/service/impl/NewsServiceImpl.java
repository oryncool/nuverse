package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import kz.tech.nuverse.model.dto.NewsDTO;
import kz.tech.nuverse.model.dto.UserDTO;
import kz.tech.nuverse.model.dto.create.NewsCreateDTO;
import kz.tech.nuverse.model.entity.NewsEntity;
import kz.tech.nuverse.model.entity.User;
import kz.tech.nuverse.repository.NewsRepository;
import kz.tech.nuverse.repository.UserRepository;
import kz.tech.nuverse.security.JwtTokenProvider;
import kz.tech.nuverse.service.NewsService;
import kz.tech.nuverse.service.UserService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Override
    public NewsDTO createNews(NewsCreateDTO newsCreateDTO, HttpServletRequest request) {
        NewsEntity entity = ModelMapperUtil.map(newsCreateDTO, NewsEntity.class);
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Invalid credentials"));
        entity.setUser(user);
        entity = newsRepository.save(entity);
        return ModelMapperUtil.map(entity, NewsDTO.class);
    }

    @Override
    public NewsDTO getNewsById(UUID id) {
        NewsEntity entity = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News not found with id " + id));
        return ModelMapperUtil.map(entity, NewsDTO.class);
    }

    @Override
    public List<NewsDTO> getAllNews() {
        return ModelMapperUtil.mapAll(newsRepository.findAll(), NewsDTO.class);
    }

    @Override
    public NewsDTO updateNews(UUID id, NewsCreateDTO newsCreateDTO) {
        NewsEntity existing = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News not found with id " + id));

        ModelMapperUtil.map(newsCreateDTO, existing);
        NewsEntity updated = newsRepository.save(existing);
        return ModelMapperUtil.map(updated, NewsDTO.class);
    }

    @Override
    public void deleteNews(UUID id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<NewsDTO> searchNews(String keyword) {
        List<NewsEntity> results = newsRepository
                .findByHeaderContainingIgnoreCaseOrTextContainingIgnoreCase(keyword, keyword);

        return ModelMapperUtil.mapAll(results, NewsDTO.class);
    }

}
