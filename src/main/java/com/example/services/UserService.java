package com.example.services;

import com.example.dtos.RegisteredUserDTO;
import com.example.dtos.UserDTO;
import com.example.dtos.UserDetailsDTO;
import com.example.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {
    RegisteredUserDTO addNewUser(UserDTO userDTO);

    boolean isUserRegistered(String desiredUsername);

    void saveUser(User user);

    UserDetailsDTO getLoggedInUser(HttpServletRequest request);

    Optional<User> findUserByUsername(String username);

    void saveVerificationTokenForUser(UserDTO userDTO, String token);

    void verifyUser(String verificationToken);
}
