package kz.tech.nuverse.service.impl;

import kz.tech.nuverse.model.dictionary.RoleDictionaryEntity;
import kz.tech.nuverse.repository.dictionary.RoleDictionaryRepository;
import kz.tech.nuverse.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleDictionaryRepository roleRepository;

    private Map<Long, String> userRoles;

    @EventListener(ApplicationReadyEvent.class)
    public void initRoles() {
        userRoles = roleRepository.findAll()
                .stream()
                .collect(Collectors.toMap(RoleDictionaryEntity::getId, role -> role.getValueEn().toUpperCase()));
    }

    @Override
    public String getRoleNameById(Long id) {
        return userRoles.get(id);
    }
}
