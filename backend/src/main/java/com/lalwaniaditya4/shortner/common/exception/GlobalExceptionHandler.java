package com.lalwaniaditya4.shortner.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lalwaniaditya4.shortner.link.exception.InvalidUrlException;
import com.lalwaniaditya4.shortner.link.exception.UnavailableShortCodeException;
import com.lalwaniaditya4.shortner.redirect.exception.InvalidShortCodeException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUrl(InvalidUrlException e)
    {
        log.warn(e.getMessage());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("Invalid URL.", e.getMessage()));
    }

    @ExceptionHandler(InvalidShortCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidShortCode(InvalidShortCodeException e)
    {
        log.warn(e.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("Invalid short code.", e.getMessage()));
    }

    @ExceptionHandler(UnavailableShortCodeException.class)
    public ResponseEntity<ErrorResponse> handleUnavailableShortCode(UnavailableShortCodeException e)
    {
        log.warn(e.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorResponse("Short code unavailable.", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCatchAll(Exception e)
    {
        log.error(e.getMessage(), e);
        
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("Internal server error", "An unexpected error occurred."));
    }
}
