package com.example.Controller;


import com.example.Service.AuthService;
import com.example.Authorization.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthController {
    @Autowired
    private AuthService authService;
        @PostMapping("/login")
        public ResponseEntity<Object> register (@RequestBody AuthenticationRequest registerRequest){
        return ResponseEntity.ok(authService.authenticate(registerRequest));
    }
}

