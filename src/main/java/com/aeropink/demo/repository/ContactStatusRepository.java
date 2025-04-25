package com.aeropink.demo.repository;

import com.aeropink.demo.entity.ContactStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContactStatusRepository extends JpaRepository<ContactStatus, UUID> {

    Optional<ContactStatus> findByDescription(String description);
}
