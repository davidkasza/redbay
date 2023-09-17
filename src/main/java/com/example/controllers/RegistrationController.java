package com.example.controllers;

import com.example.dtos.ErrorDTO;
import com.example.dtos.UserDTO;
import com.example.services.GeneralUtility;
import com.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        String parameter = "";

        if (GeneralUtility.isEmptyOrNull(userDTO.getUsername())) {
            parameter = "Username";
        } else if (GeneralUtility.isEmptyOrNull(userDTO.getEmail())) {
            parameter = "Email";
        } else if (GeneralUtility.isEmptyOrNull(userDTO.getPassword())) {
            parameter = "Password";
        }

        if (parameter.length() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", parameter + " is required!"));
        }

        if (userDTO.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorDTO("error", "The password must be at least 8 characters!"));
        }

        if (userService.isUserRegistered(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO("error", "The user already exists!"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewUser(userDTO));
    }
}
