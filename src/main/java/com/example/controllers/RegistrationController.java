package com.example.controllers;

import com.example.dtos.ErrorDTO;
import com.example.dtos.RegisteredUserDTO;
import com.example.dtos.UserDTO;
import com.example.services.GeneralUtility;
import com.example.services.UserService;
import com.example.verification.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class RegistrationController {
    private UserService userService;

    private EmailService emailService;

    public RegistrationController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
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

        RegisteredUserDTO registeredUserDTO = userService.addNewUser(userDTO);

        try {
            emailService.sendEmail(userDTO);
        } catch (MessagingException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", "Something went wrong."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserDTO);
    }
}
