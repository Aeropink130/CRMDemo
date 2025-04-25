package com.aeropink.demo.service.serviceImpl;

import com.aeropink.demo.DTO.UserDTO;
import com.aeropink.demo.entity.Person;
import com.aeropink.demo.entity.AppUser;
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
    public UserDTO saveUser(CreateUserRequest cur) {

        Person existingPeson = personRepository.findByEmail(cur.getEmail()).orElse(null);

        if (existingPeson != null) {
            return null;
        }

        Person newPerson = new Person();
        newPerson.setFirstName(cur.getFirstName());
        newPerson.setLastName(cur.getLastName());
        newPerson.setEmail(cur.getEmail());

        personRepository.save(newPerson);

        AppUser newUser = new AppUser();
        newUser.setPerson(newPerson);
        newUser.setUserName(cur.getUserName());
        newUser.setPassword(cur.getPassword());
        userRepository.save(newUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(newUser.getUserName());
        userDTO.setFirstName(newPerson.getFirstName());
        userDTO.setLastName(newPerson.getLastName());
        userDTO.setEmail(newPerson.getEmail());

        return userDTO;

    }

    @Override
    public Optional<AppUser> findUserByIdComplete(UUID id) {
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }

    @Override
    public Optional<UserDTO> findUserById(UUID id) {

        AppUser user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return Optional.empty();
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUserName());
        userDTO.setFirstName(user.getPerson().getFirstName());
        userDTO.setLastName(user.getPerson().getLastName());
        userDTO.setEmail(user.getPerson().getEmail());

        return Optional.of(userDTO);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }

    @Override
    public AppUser updateUser(AppUser user) {
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
