package com.example.exception.handlers;

import com.example.exception.*;
import com.example.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@RestControllerAdvice
public class GlobalExceptionHandler extends ExceptionHandlerExceptionResolver {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(new ErrorDTO("error", exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException exception) {
        return new ResponseEntity<>(new ErrorDTO("error", "Invalid token: " + exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = SellableItemNotFoundException.class)
    public ResponseEntity<Object> handleSellableItemNotFoundException(SellableItemNotFoundException exception) {
        return new ResponseEntity<>(new ErrorDTO("error", exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BidTimeExpiredException.class)
    public ResponseEntity<Object> handleBidTimeExpiredException(BidTimeExpiredException exception) {
        return new ResponseEntity<>(new ErrorDTO("error", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidBidPriceException.class)
    public ResponseEntity<Object> handleInvalidBidPriceException(InvalidBidPriceException exception) {
        return new ResponseEntity<>(new ErrorDTO("error", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SellableItemDoesNotBelongToUserException.class)
    public ResponseEntity<Object> handleSellableItemDoesNotBelongToUserException(SellableItemDoesNotBelongToUserException exception) {
        return new ResponseEntity<>(new ErrorDTO("error", exception.getMessage()), HttpStatus.FORBIDDEN);
    }
}