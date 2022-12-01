package com.example.whatstocleanbackend.exception;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity exceptionHandler(RuntimeException exception) {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler(DateException exception) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler(IndexException exception) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
