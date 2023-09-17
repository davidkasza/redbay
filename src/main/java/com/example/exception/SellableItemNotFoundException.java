package com.example.exception;

public class SellableItemNotFoundException extends RuntimeException {
    public SellableItemNotFoundException() {
        super("Id not found");
    }
}