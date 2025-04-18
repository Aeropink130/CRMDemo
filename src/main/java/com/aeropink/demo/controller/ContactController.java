package com.aeropink.demo.controller;

import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.model.CreateContactRequest;
import com.aeropink.demo.service.ContactService;
import com.aeropink.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createContact(@RequestBody CreateContactRequest ccr) {
        AppUser appUser = userService.findUserById(ccr.getAppUserId()).orElse(null);
        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encotró el usuario");
        }
        Contact createdContact = contactService.saveContact(ccr, appUser);

        if (createdContact == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya se encuentra registrado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getContacts(@PathVariable UUID id) {

        List<Contact> contactList = contactService.findContactsByUser(id);
        return ResponseEntity.ok(contactList);
    }
}
