package com.peak.collection.email.exception;

public class EmailException extends RuntimeException{
    public EmailException() {}

    public EmailException(String msg) {
        super(msg);
    }
}
