package com.example.yachting.security.auth.register;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"${client.origin}"})
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public Long registerUser(@Valid @RequestBody final RegisterCommand registerCommand) {
        return registerService.registerUser(registerCommand);
    }
}
