package com.example.services;

import com.example.security.AuthenticationRequest;

public interface LoginService {

    String createAuthenticationToken(AuthenticationRequest authenticationRequest);

    void authenticate(AuthenticationRequest authenticationRequest);

    void checkEmailVerification(String username);
}
