package com.example.yachting.security.auth.register;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"${client.origin}"})
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public Long registerUser(@Valid @RequestBody final RegisterCommand registerCommand) {
        return registerService.registerUser(registerCommand);
    }
}
