package com.chatlet.chatlet.advices;

import com.chatlet.chatlet.exceptions.AccountExistsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlingAdvice  {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(AccountExistsException.class)
    public String handleAccountExistsException(AccountExistsException e) {
        return e.getMessage();
    }
}
