package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.PhonebookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhonebookRepository extends JpaRepository<PhonebookEntity, UUID> {

    List<PhonebookEntity> findByPhoneContainingIgnoreCase(String phone);
}