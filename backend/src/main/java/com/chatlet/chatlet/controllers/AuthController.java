package com.chatlet.chatlet.controllers;


import com.chatlet.chatlet.data.dtos.RegisterDTO;
import com.chatlet.chatlet.data.dtos.ResponseDTO;
import com.chatlet.chatlet.services.AuthService;
import com.chatlet.chatlet.services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDTO> token(Authentication authentication, HttpServletResponse response) {

        Cookie cookie = new Cookie("token",tokenService.generateJwtToken(authentication));
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setAttribute("SameSite","lax");
        cookie.setPath("/");

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage(cookie.getValue());
        response.addCookie(cookie);

        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/validate")
    public ResponseEntity<ResponseDTO> token() {



        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage(authService.getUsername());


        return ResponseEntity.ok(responseDTO);

    }




    @DeleteMapping("/account")
    public ResponseEntity<ResponseDTO> deleteAccount( ) {
        authService.deleteAccount();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Account deleted successfully");
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/disable")
    public ResponseEntity<ResponseDTO> disableAccount() {
        authService.disableAccount();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Account disabled successfully");
        return ResponseEntity.ok(responseDTO);


    }


}
