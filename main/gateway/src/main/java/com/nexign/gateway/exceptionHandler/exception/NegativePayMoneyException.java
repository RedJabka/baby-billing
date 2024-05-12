package com.nexign.gateway.exceptionHandler.exception;

public class NegativePayMoneyException extends RuntimeException {
    
    public NegativePayMoneyException(String message) {
        super(message);
    }
}
