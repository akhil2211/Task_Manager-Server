package com.example.Authorization;

import com.example.CustomContextHolder.AppContextHolder;
import com.example.Model.User;
import com.example.Repository.TokenRepo;
import com.example.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException
    {
       final String authHeader= request.getHeader("Authorization");
       final String jwtToken;
       final String UserName;

       if(authHeader == null || !authHeader.startsWith("Bearer")){
           filterChain.doFilter(request,response);
           return;
       }

       jwtToken=authHeader.substring(7);
       UserName=jwtService.extractUsername(jwtToken);
       if(UserName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           UserDetails userDetails=this.userDetailsService.loadUserByUsername(UserName);
           var isTokenValid=tokenRepo.findByToken(jwtToken)
                   .map(t-> !t.isExpired() && !t.isRevoked())
                   .orElse(false);
           if(jwtService.isTokenValid(jwtToken,userDetails) && isTokenValid){
               UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               User user=userRepository.findByUsername(userDetails.getUsername()).orElse(null);
               AppContextHolder.setUserId(user.getId());
           }
       }

       filterChain.doFilter(request, response);

       }
    }

