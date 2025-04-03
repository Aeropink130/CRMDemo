package com.aeropink.demo.service.serviceImpl;

import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.service.ContactService;
import com.aeropink.demo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact findContactById(UUID id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact updateContact(Contact contact) {
        return null;
    }

    @Override
    public void deleteContact(UUID id) {

    }
}
