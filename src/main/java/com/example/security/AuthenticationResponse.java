package com.example.security;

public class AuthenticationResponse {

    private String status;

    private String token;

    public AuthenticationResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }
}
