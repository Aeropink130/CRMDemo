package com.aeropink.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactRequest {

    private UUID appUserId;
    private String firstName;
    private String lastName;
    private String email;
}
