package com.example.yachting.exception.exceptions;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

}