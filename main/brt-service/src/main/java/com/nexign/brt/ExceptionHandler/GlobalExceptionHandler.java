package com.nexign.brt.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nexign.brt.domain.StatusMessage;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<StatusMessage> catchEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(
            StatusMessage.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getLocalizedMessage())
                .build(),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StatusMessage> catchException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(
            StatusMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getLocalizedMessage())
                .build(),
            HttpStatus.BAD_REQUEST);
    }
}
