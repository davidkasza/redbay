package com.example.exception;

public class InvalidBidPriceException extends NumberFormatException {
    public InvalidBidPriceException() {
        super("The bid price cannot be equal or less than the last bid price or the start price!");
    }
}