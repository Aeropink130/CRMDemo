package com.aeropink.demo.repository;

import com.aeropink.demo.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    List<Contact> findContactsByAppUser_Id(UUID id);

    Long countByAppUser_IdAndStatusDescription(UUID appUserId, String status);
}
