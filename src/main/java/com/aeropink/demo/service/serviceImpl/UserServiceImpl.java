package com.aeropink.demo.service.serviceImpl;

import com.aeropink.demo.entity.Person;
import com.aeropink.demo.entity.User;
import com.aeropink.demo.model.CreateUserRequest;
import com.aeropink.demo.repository.PersonRepository;
import com.aeropink.demo.service.UserService;
import com.aeropink.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    public UserServiceImpl(UserRepository userRepository, PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    @Override
    public User saveUser(CreateUserRequest cur) {

        Person newPerson = new Person();
        newPerson.setFirstName(cur.getFirstName());
        newPerson.setLastName(cur.getLastName());
        newPerson.setEmail(cur.getEmail());

        personRepository.save(newPerson);

        User newUser = new User();
        newUser.setPerson(newPerson);
        newUser.setUserName(cur.getUserName());
        newUser.setPassword(cur.getPassword());


        return userRepository.save(newUser);

    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
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
