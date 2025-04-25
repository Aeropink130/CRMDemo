package com.aeropink.demo.controller;

import com.aeropink.demo.DTO.LoginDTO;
import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.model.LoginRequest;
import com.aeropink.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        AppUser user  = userService.findUserByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }

        String token = "fake-token";

        LoginDTO response = new LoginDTO();
        response.setId(user.getId());
        response.setToken(token);
        response.setId(user.getId());
        response.setName(user.getPerson().getFirstName() + " " + user.getPerson().getLastName());
        response.setEmail(user.getPerson().getEmail());

        return ResponseEntity.ok(response);
    }
}
