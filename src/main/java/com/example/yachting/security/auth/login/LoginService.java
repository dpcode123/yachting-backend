package com.example.yachting.security.auth.login;

import com.example.yachting.security.jwt.JwtToken;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<JwtToken> loginUser(LoginCommand loginCommand);

}
