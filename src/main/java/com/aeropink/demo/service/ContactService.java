package com.aeropink.demo.service;

import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.model.CreateContactRequest;

import java.util.UUID;

public interface ContactService {

    public Contact saveContact(CreateContactRequest ccr);

    public Contact findContactById(UUID id);

    public Contact updateContact(Contact contact);

    public void deleteContact(UUID id);
}
