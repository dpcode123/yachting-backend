package com.example.yachting.security.auth.login;

import com.example.yachting.security.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"${client.origin}"})
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<JwtToken> authenticate(@Valid @RequestBody LoginCommand loginCommand) {
        return loginService.loginUser(loginCommand);
    }


}
