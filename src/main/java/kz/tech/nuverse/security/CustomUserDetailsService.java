package kz.tech.nuverse.security;

import kz.tech.nuverse.model.entity.User;
import kz.tech.nuverse.repository.UserRepository;
import kz.tech.nuverse.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        String roleName = roleService.getRoleNameById(user.getRole().getId());
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));
        return new CustomUserDetails(user, authorities);
    }
}
