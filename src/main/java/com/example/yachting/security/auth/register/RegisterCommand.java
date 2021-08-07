package com.example.yachting.security.auth.register;

import javax.validation.constraints.NotBlank;

public record RegisterCommand (
        @NotBlank(message = "Username is empty")
        String username,

        @NotBlank(message = "Email is empty")
        String email,

        @NotBlank(message = "Password is empty")
        String password
) {}
