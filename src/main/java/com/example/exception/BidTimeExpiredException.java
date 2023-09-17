package com.example.exception;

import java.time.DateTimeException;

public class BidTimeExpiredException extends DateTimeException {
    public BidTimeExpiredException() {
        super("The time has expired, you can not set a bid for this product anymore!");
    }
}