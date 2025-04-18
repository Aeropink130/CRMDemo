package com.aeropink.demo.service.serviceImpl;

import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.entity.Person;
import com.aeropink.demo.model.CreateContactRequest;
import com.aeropink.demo.repository.PersonRepository;
import com.aeropink.demo.repository.UserRepository;
import com.aeropink.demo.service.ContactService;
import com.aeropink.demo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    public ContactServiceImpl(ContactRepository contactRepository, PersonRepository personRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Contact saveContact(CreateContactRequest ccr, AppUser user) {

        Person existingPerson = personRepository.findByEmail(ccr.getEmail()).orElse(null);

        if (existingPerson != null) {
            return null;
        }

        Person newPerson = new Person();
        newPerson.setFirstName(ccr.getFirstName());
        newPerson.setLastName(ccr.getLastName());
        newPerson.setEmail(ccr.getEmail());

        personRepository.save(newPerson);

        Contact newContact = new Contact();
        newContact.setPerson(newPerson);
        newContact.setAppUser(user);

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

    @Override
    public List<Contact> findContactsByUser(UUID id) {
        return contactRepository.findContactsByAppUser_Id(id);
    }
}
