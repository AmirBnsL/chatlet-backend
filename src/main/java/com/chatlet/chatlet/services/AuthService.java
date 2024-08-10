package com.chatlet.chatlet.services;

import com.chatlet.chatlet.data.dtos.RegisterDTO;
import com.chatlet.chatlet.data.entities.Auth;
import com.chatlet.chatlet.exceptions.AccountExistsException;
import com.chatlet.chatlet.repositories.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.channels.AcceptPendingException;


@Service
@AllArgsConstructor
public class AuthService {

    private AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;
    public void register(RegisterDTO registerDTO) throws AccountExistsException {

        if (authRepository.existsByEmail(registerDTO.getEmail()) ) {
            throw new AccountExistsException("Email already exists");

        }

        if (authRepository.existsByUsername(registerDTO.getUsername()) ) {
            throw new AccountExistsException("Username already exists");

        }

        else {
            Auth auth = new Auth();
            auth.setUsername(registerDTO.getUsername());
            auth.setEmail(registerDTO.getEmail());
            auth.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            auth.setIsDisabled(false);

            authRepository.save(auth);

        }

    }


}
