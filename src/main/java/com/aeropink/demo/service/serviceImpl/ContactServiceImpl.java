package com.aeropink.demo.service.serviceImpl;

import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.entity.Person;
import com.aeropink.demo.model.CreateContactRequest;
import com.aeropink.demo.repository.PersonRepository;
import com.aeropink.demo.service.ContactService;
import com.aeropink.demo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final PersonRepository personRepository;

    public ContactServiceImpl(ContactRepository contactRepository, PersonRepository personRepository) {
        this.contactRepository = contactRepository;
        this.personRepository = personRepository;
    }


    @Override
    public Contact saveContact(CreateContactRequest ccr) {

        Person newPerson = new Person();
        newPerson.setFirstName(ccr.getFirstName());
        newPerson.setLastName(ccr.getLastName());
        newPerson.setEmail(ccr.getEmail());

        personRepository.save(newPerson);

        Contact newContact = new Contact();
        newContact.setPerson(newPerson);

        return contactRepository.save(newContact);
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
