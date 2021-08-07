package com.example.yachting.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class TransactionFailedException extends RuntimeException {

    public TransactionFailedException(String message) { super(message); }

}
