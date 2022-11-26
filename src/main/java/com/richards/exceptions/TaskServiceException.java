package com.richards.exceptions;

public class TaskServiceException extends RuntimeException {
    public TaskServiceException(String message) {
        super(message);
    }
}
