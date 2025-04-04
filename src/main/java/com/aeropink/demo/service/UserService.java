package com.aeropink.demo.service;

import com.aeropink.demo.entity.User;
import com.aeropink.demo.model.CreateUserRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public User saveUser(CreateUserRequest cur);

    public Optional<User> findUserById(UUID id);

    public User updateUser(User user);

    public void deleteUser(UUID id);

}
