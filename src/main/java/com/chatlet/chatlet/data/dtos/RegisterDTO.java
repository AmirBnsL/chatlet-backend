package com.chatlet.chatlet.data.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
