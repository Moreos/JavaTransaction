package com.moreos.exceptions;

public class IllegalTransactionException extends RuntimeException{
    public IllegalTransactionException(String message) {
        super(message);
    }
}
