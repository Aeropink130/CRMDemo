package com.aeropink.demo.controller;

import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.model.CreateContactRequest;
import com.aeropink.demo.service.ContactService;
import com.aeropink.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Contact> createContact(@RequestBody CreateContactRequest ccr) {
        AppUser appUser = userService.findUserById(ccr.getAppUserId()).orElse(null);
        if (appUser == null) {
            return (ResponseEntity<Contact>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        Contact createdContact = contactService.saveContact(ccr);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }
}
