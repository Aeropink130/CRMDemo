package com.aeropink.demo.service.serviceImpl;

import com.aeropink.demo.entity.User;
import com.aeropink.demo.service.UserService;
import com.aeropink.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {

        return userRepository.save(user);

    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteUserById(id);

        if (userRepository.existsById(id)) {
            throw new RuntimeException("No se pudo eliminar el usuario.");
        }
    }
}
