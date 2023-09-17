package com.example.services;

import com.example.dtos.RegisteredUserDTO;
import com.example.dtos.UserDTO;
import com.example.entities.User;

public interface UserService {
    RegisteredUserDTO addNewUser(UserDTO userDTO);

    boolean isUserRegistered(String desiredUsername);

    void saveUser(User user);
}
