package com.example.controllers;

import com.example.dtos.ErrorDTO;
import com.example.security.AuthenticationRequest;
import com.example.security.AuthenticationResponse;
import com.example.services.GeneralUtility;
import com.example.services.LoginService;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private LoginService loginService;

    private UserService userService;

    @Autowired
    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        String invalidParameter = "";

        if (GeneralUtility.isEmptyOrNull(authenticationRequest.getUsername())) {
            invalidParameter = "Username";
        } else if (GeneralUtility.isEmptyOrNull(authenticationRequest.getPassword())) {
            invalidParameter = "Password";
        }

        if (!invalidParameter.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("error", invalidParameter + " is required!"));
        }

        if (!userService.isUserRegistered(authenticationRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("error", "The user (" + authenticationRequest.getUsername() + ") does not exist!"));
        }

        loginService.authenticate(authenticationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationResponse("ok", loginService.createAuthenticationToken(authenticationRequest)));
    }
}
