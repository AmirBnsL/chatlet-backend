package com.chatlet.chatlet.controllers;


import com.chatlet.chatlet.data.dtos.RegisterDTO;
import com.chatlet.chatlet.data.dtos.ResponseDTO;
import com.chatlet.chatlet.services.AuthService;
import com.chatlet.chatlet.services.TokenService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Validated @RequestBody RegisterDTO registerDTO) {

        authService.register(registerDTO);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("User registered successfully");

        return ResponseEntity.ok(responseDTO);

    }


    @GetMapping("/token")
    public ResponseEntity<ResponseDTO> token(Authentication authentication) {


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage(tokenService.generateJwtToken(authentication));

        return ResponseEntity.ok(responseDTO);

    }





}
