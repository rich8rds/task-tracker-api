package com.richards.apiresponse;

import com.richards.exceptions.SessionIdNotFoundException;
import com.richards.exceptions.TaskNotFoundException;
import com.richards.exceptions.TaskServiceException;
import com.richards.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = TaskServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleException(TaskServiceException ex) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage()).build();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(UserNotFoundException ex) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(ex.getMessage()).build();
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(TaskNotFoundException ex) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(ex.getMessage()).build();
    }

    @ExceptionHandler(value = SessionIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(SessionIdNotFoundException ex) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(ex.getMessage()).build();
    }

}
