package com.example.controllers;

import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-data")
    public ResponseEntity<?> getUserDetails(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLoggedInUser(request));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmailAddress(@RequestParam String token) {
        userService.verifyUser(token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Your email has been verified");
    }
}
