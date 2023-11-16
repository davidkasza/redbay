package com.example.exception;

public class EmailNotVerifiedException extends RuntimeException {
    public EmailNotVerifiedException() {
        super("Your email has not been verified yet!");
    }
}
