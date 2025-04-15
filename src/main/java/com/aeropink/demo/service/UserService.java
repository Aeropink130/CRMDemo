package com.aeropink.demo.service;

import com.aeropink.demo.entity.AppUser;
import com.aeropink.demo.model.CreateUserRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public AppUser saveUser(CreateUserRequest cur);

    public Optional<AppUser> findUserById(UUID id);

    public AppUser updateUser(AppUser user);

    public void deleteUser(UUID id);

}
