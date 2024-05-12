package com.nexign.gateway.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.exceptionHandler.exception.NegativePayMoneyException;

import feign.FeignException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler
        public ResponseEntity<StatusMessage> catchBadCredentialsException(BadCredentialsException e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity<>(
                                StatusMessage.builder()
                                                .status(HttpStatus.UNAUTHORIZED.value())
                                                .message(e.getLocalizedMessage())
                                                .build(),
                                HttpStatus.UNAUTHORIZED);
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

        @ExceptionHandler
        public ResponseEntity<StatusMessage> catchNegativePayMoneyException(NegativePayMoneyException e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity<>(
                                StatusMessage.builder()
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .message(e.getLocalizedMessage())
                                                .build(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler
        public ResponseEntity<StatusMessage> catchFeignException(FeignException e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity<>(
                                StatusMessage.builder()
                                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                .message(e.getLocalizedMessage())
                                                .build(),
                                HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
