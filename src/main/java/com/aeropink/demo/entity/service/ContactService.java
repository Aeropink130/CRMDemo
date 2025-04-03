package com.aeropink.demo.entity.service;

import com.aeropink.demo.entity.Contact;

import java.util.UUID;

public interface ContactService {

    public Contact saveContact(Contact contact);

    public Contact findContactById(UUID id);

    public Contact updateContact(Contact contact);

    public void deleteContact(UUID id);
}
