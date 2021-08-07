package com.example.yachting.exception;

import com.example.yachting.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiException> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<ApiException> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(TransactionFailedException.class)
    public final ResponseEntity<ApiException> handleTransactionFailedException(TransactionFailedException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        ApiException apiException = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ApiException> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ApiException> handleCustomException(CustomException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiException> handleAllExceptions(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiException, httpStatus);
    }
}

