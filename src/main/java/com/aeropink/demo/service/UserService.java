package com.aeropink.demo.service;

import com.aeropink.demo.DTO.UserDTO;
import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.model.CreateUserRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public UserDTO saveUser(CreateUserRequest cur);

    public Optional<AppUser> findUserByIdComplete(UUID id);

    public Optional<UserDTO> findUserById(UUID id);

    public AppUser findUserByUsername(String username);

    public AppUser updateUser(AppUser user);

    public void deleteUser(UUID id);

}
