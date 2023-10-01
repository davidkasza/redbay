package com.example.exception;

public class SellableItemDoesNotBelongToUserException extends RuntimeException {
    public SellableItemDoesNotBelongToUserException() {
        super("This item does not belong to you!");
    }
}