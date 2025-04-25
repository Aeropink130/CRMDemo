package com.aeropink.demo.service;

import com.aeropink.demo.DTO.ContactDTO;
import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.model.CreateContactRequest;

import java.util.List;
import java.util.UUID;

public interface ContactService {

    public ContactDTO saveContact(CreateContactRequest ccr, AppUser user);

    public Contact findContactById(UUID id);

    public ContactDTO updateContact(UUID id, ContactDTO contact);

    public void deleteContact(UUID id);

    public List<ContactDTO> findContactsByUser(UUID id);

    public void updateStatus(UUID id, String newStatus);

    public long countByStatusAndUser(UUID userId, String status);

}
