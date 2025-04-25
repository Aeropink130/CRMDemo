package com.aeropink.demo.controller;

import com.aeropink.demo.DTO.ContactDTO;
import com.aeropink.demo.DTO.UserDTO;
import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.entity.Contact;
import com.aeropink.demo.model.CreateContactRequest;
import com.aeropink.demo.service.ContactService;
import com.aeropink.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createContact(@RequestBody CreateContactRequest ccr) {
        if (ccr.getAppUserId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encotró el usuario");
        }
        AppUser appUser = userService.findUserByIdComplete(ccr.getAppUserId()).orElse(null);
        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encotró el usuario");
        }
        ContactDTO createdContact = contactService.saveContact(ccr, appUser);

        if (createdContact == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya se encuentra registrado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getContacts(@PathVariable UUID id) {

        List<ContactDTO> contactList = contactService.findContactsByUser(id);
        return ResponseEntity.ok(contactList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable UUID id, @RequestBody ContactDTO updatedData) {
        try {
            ContactDTO updated = contactService.updateContact(id, updatedData);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contacto no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar contacto");
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateContactStatus(@PathVariable UUID id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        if (newStatus == null || (!newStatus.equals("Cliente") && !newStatus.equals("Prospecto"))) {
            return ResponseEntity.badRequest().body("Estatus no válido");
        }

        try {
            contactService.updateStatus(id, newStatus);
            return ResponseEntity.ok("Estatus actualizado correctamente");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar estatus");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable UUID id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok(Map.of("message", "Contacto eliminado con éxito"));
    }

    @GetMapping("/stats/{userId}")
    public ResponseEntity<Map<String, Long>> getContactStats(@PathVariable UUID userId) {
        long clientes = contactService.countByStatusAndUser(userId, "Cliente");
        long prospectos = contactService.countByStatusAndUser(userId, "Prospecto");

        return ResponseEntity.ok(Map.of(
                "clientes", clientes,
                "prospectos", prospectos
        ));
    }

}
