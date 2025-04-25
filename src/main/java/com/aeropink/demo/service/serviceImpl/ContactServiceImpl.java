package com.aeropink.demo.service.serviceImpl;

import com.aeropink.demo.DTO.ContactDTO;
import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.entity.ContactStatus;
import com.aeropink.demo.entity.Person;
import com.aeropink.demo.model.CreateContactRequest;
import com.aeropink.demo.repository.ContactStatusRepository;
import com.aeropink.demo.repository.PersonRepository;
import com.aeropink.demo.repository.UserRepository;
import com.aeropink.demo.service.ContactService;
import com.aeropink.demo.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final ContactStatusRepository contactStatusRepository;

    public ContactServiceImpl(ContactRepository contactRepository, PersonRepository personRepository, UserRepository userRepository, ContactStatusRepository contactStatusRepository) {
        this.contactRepository = contactRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.contactStatusRepository = contactStatusRepository;
    }


    @Override
    public ContactDTO saveContact(CreateContactRequest ccr, AppUser user) {

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

        ContactStatus contactStatus = contactStatusRepository.findByDescription("Prospecto").orElse(null);

        newContact.setStatus(contactStatus);
        contactRepository.save(newContact);

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(newContact.getId());
        contactDTO.setFirstName(newContact.getPerson().getFirstName());
        contactDTO.setLastName(newContact.getPerson().getLastName());
        contactDTO.setEmail(newContact.getPerson().getEmail());
        contactDTO.setStatus(contactStatus.getDescription());

        return contactDTO;
    }

    @Override
    public Contact findContactById(UUID id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public ContactDTO updateContact(UUID id, ContactDTO contact) {
        Contact existing = contactRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Contacto no encontrado"));

        Person existingPerson = existing.getPerson();
        existingPerson.setFirstName(contact.getFirstName());
        existingPerson.setLastName(contact.getLastName());
        existingPerson.setEmail(contact.getEmail());

        existing.setPerson(existingPerson);

        Contact saved = contactRepository.save(existing);
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(saved.getId());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setStatus(contact.getStatus());

        return contactDTO;
    }

    @Override
    public void deleteContact(UUID id) {
        if (!contactRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el contacto con ID: " + id);
        }

        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDTO> findContactsByUser(UUID id) {

        List<Contact> contacts = contactRepository.findContactsByAppUser_Id(id);

        return contacts.stream()
                .map(contact -> {
                    ContactDTO dto = new ContactDTO();
                    dto.setId(contact.getId());
                    dto.setFirstName(contact.getPerson().getFirstName());
                    dto.setLastName(contact.getPerson().getLastName());
                    dto.setEmail(contact.getPerson().getEmail());
                    dto.setStatus(contact.getStatus().getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(UUID id, String newStatus) {
        Contact contact = contactRepository.findById(id).orElseThrow();

        ContactStatus newContactStatus = contactStatusRepository.findByDescription(newStatus).orElse(null);

        contact.setStatus(newContactStatus);
        contactRepository.save(contact);
    }

    @Override
    public long countByStatusAndUser(UUID userId, String status) {
        return contactRepository.countByAppUser_IdAndStatusDescription(userId, status);
    }
}
