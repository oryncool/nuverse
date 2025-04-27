package kz.tech.nuverse.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import kz.tech.nuverse.model.dto.ProfessorDTO;
import kz.tech.nuverse.model.dto.UserDTO;
import kz.tech.nuverse.model.entity.User;
import kz.tech.nuverse.repository.UserRepository;
import kz.tech.nuverse.security.JwtTokenProvider;
import kz.tech.nuverse.service.UserService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Invalid credentials"));
        UserDTO UserDTO = ModelMapperUtil.map(user, UserDTO.class);
        return UserDTO;
    }

    @Override
    public UserDTO getUserInfo(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Invalid credentials"));
        return ModelMapperUtil.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ModelMapperUtil.mapAll(users, UserDTO.class);
    }

    @Override
    public List<UserDTO> searchUsers(String search) {
        return ModelMapperUtil.mapAll(
                userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrLastNameContainingIgnoreCase(search, search, search),
                UserDTO.class
        );
    }
}
