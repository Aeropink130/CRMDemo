package com.aeropink.demo.entity.service;

import com.aeropink.demo.entity.User;

import java.util.UUID;

public interface UserService {

    public User saveUser(User user);

    public User findUserById(UUID id);

    public User updateUser(User user);

    public void deleteUser(UUID id);

}
