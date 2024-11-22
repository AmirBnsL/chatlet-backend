package com.chatlet.chatlet.advices;

import com.chatlet.chatlet.data.dtos.ResponseDTO;
import com.chatlet.chatlet.exceptions.AccountExistsException;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jshell.Snippet;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ErrorHandlingAdvice  {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(Exception e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(AccountExistsException.class)
    public ResponseEntity<ResponseDTO> handleAccountExistsException(AccountExistsException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setMessage(e.getMessage());
        return ResponseEntity.status(409).body(responseDTO);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setMessage(e.getMessage());
        return ResponseEntity.status(409).body(responseDTO);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseDTO> handleIOException(IOException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setMessage(e.getMessage());
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(responseDTO);
    }

}
