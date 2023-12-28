package com.example.Authorization;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${spring.jwt.timeToLeave}")
    private Integer timeToLeave;
    private static final String key= "7B37E18A5B2A23A85F5CCAB79F8497B37E18A5B2A23A85F5CCAB79F849";
    public String extractUsername(String jwt) {
        return extractClaim(jwt,Claims::getSubject);
    }
    public <T> T extractClaim(String jwt, Function<Claims,T> claimsResolver){
        final Claims claims =extractClaims(jwt) ;
        return claimsResolver.apply(claims);
    }
public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
}
    public String generateToken(Map< String,Object> extraClaims, UserDetails userDetails){

        return Jwts
                .builder()
                .setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+timeToLeave)).signWith(getSignInkey(), SignatureAlgorithm.HS256).compact();
    }
   public boolean isTokenValid(String jwt,UserDetails userDetails){
        final String username=extractUsername(jwt);
        return username.equals(userDetails.getUsername()) &&! isTokenExpired(jwt);
   }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return extractClaim(jwt,Claims::getExpiration);
    }

    private Claims extractClaims(String jwt){
     return Jwts.parserBuilder().setSigningKey(getSignInkey()).build().parseClaimsJws(jwt).getBody();

    }

    private Key getSignInkey() {
        byte[] keyBytes= Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
