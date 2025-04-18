package com.aeropink.demo.service;

import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.model.CreateContactRequest;

import java.util.List;
import java.util.UUID;

public interface ContactService {

    public Contact saveContact(CreateContactRequest ccr, AppUser user);

    public Contact findContactById(UUID id);

    public Contact updateContact(Contact contact);

    public void deleteContact(UUID id);

    public List<Contact> findContactsByUser(UUID id);
}
