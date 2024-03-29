package com.example.Service;

import com.example.Authorization.AuthenticationRequest;
import com.example.Authorization.JwtService;
import com.example.Model.*;
import com.example.Repository.OrganizationRepo;
import com.example.Repository.RoleRepo;
import com.example.Repository.TokenRepo;
import com.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service

public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public final AuthenticationManager authenticationManager;


    private final TokenRepo tokenRepo;

    @Autowired
    public  AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepo tokenRepo){
        this.userRepository=userRepository;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
        this.tokenRepo = tokenRepo;
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token=new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepo.save(token);
    }

    private void revokeAllTokens(User user){
        var validToken=tokenRepo.findAllValidTokensByUser(user.getId());
        if(validToken.isEmpty())
            return;
        validToken.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepo.saveAll(validToken);
    }

    public Object authenticate(AuthenticationRequest registerRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registerRequest.getUsername(),
                            registerRequest.getPassword())

            );
            var user = userRepository.findByUsername(registerRequest.getUsername()).orElseThrow(() -> new BadCredentialsException("User not found"));

            var jwtToken = jwtService.generateToken(user);
            revokeAllTokens(user);
            saveUserToken(user,jwtToken);
            Map<String,String> res=new HashMap<>();
            res.put("response",jwtToken);
            res.put("status","True");
            return res;
        }
        catch(AuthenticationException e){
            Map<String,String> res=new HashMap<>();
            res.put("response","Username Or Password does not match!");
            res.put("status","False");
            return res;
        }
               }
}
