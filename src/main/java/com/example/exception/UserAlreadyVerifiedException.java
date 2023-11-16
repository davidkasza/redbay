package com.example.exception;

public class UserAlreadyVerifiedException extends RuntimeException {
    public UserAlreadyVerifiedException() {
        super("This user has already been verified!");
    }
}