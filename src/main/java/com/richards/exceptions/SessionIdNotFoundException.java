package com.richards.exceptions;

public class SessionIdNotFoundException extends RuntimeException {
    public SessionIdNotFoundException(String message) {
        super(message);
    }
}
