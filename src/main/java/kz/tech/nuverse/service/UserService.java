package kz.tech.nuverse.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.tech.nuverse.model.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO getUserById(UUID id);

    UserDTO getUserInfo(HttpServletRequest request);

    List<UserDTO> getAllUsers();

    List<UserDTO> searchUsers(String search);
}
