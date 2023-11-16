package com.example.services;

import com.example.exception.EmailNotVerifiedException;
import com.example.exception.UserNotFoundException;
import com.example.security.AuthenticationRequest;
import com.example.security.JwtUtil;
import com.example.security.RedbayUserDetailsService;
import com.example.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private RedbayUserDetailsService redbayUserDetailsService;

    private UserService userService;

    private JwtUtil jwtTokenUtil;

    private AuthenticationManager authenticationManager;

    @Autowired
    public LoginServiceImpl(RedbayUserDetailsService redbayUserDetailsService, UserService userService, JwtUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.redbayUserDetailsService = redbayUserDetailsService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String createAuthenticationToken(AuthenticationRequest authenticationRequest) {
        UserDetailsImpl userDetails = redbayUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public void authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void checkEmailVerification(String username) {
        if (!userService.findUserByUsername(username).get().getVerified()) {
            throw new EmailNotVerifiedException();
        }
    }
}
