package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class DefaultExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> defaultException(final Exception exp, final HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(
                String.format("Exception, Details: %s", exp));
    }

}
