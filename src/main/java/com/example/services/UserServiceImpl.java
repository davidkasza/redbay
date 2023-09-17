package com.example.services;

import com.example.dtos.RegisteredUserDTO;
import com.example.dtos.UserDTO;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder;

    private MapperService mapperService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder, MapperService mapperService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.mapperService = mapperService;
    }

    public RegisteredUserDTO addNewUser(UserDTO userDTO) {
        User user = mapperService.convertUserDTOToUser(userDTO);

        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        return mapperService.convertUserToRegisteredUserDTO(user);
    }

    public boolean isUserRegistered(String desiredUsername) {
        return !userRepository.findUserByUsername(desiredUsername).isEmpty();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}