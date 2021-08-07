package com.example.yachting.security.auth.login;

import javax.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank(message = "Username is empty")
        String username,

        @NotBlank(message = "Password is empty")
        String password
) {}
