package com.bijuli.whatsappClone.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenProvider {

  SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  public String generateToken(Authentication authentication) {
    String jwt = Jwts.builder().issuer("somb").issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + 86400000))
        .claim("email", authentication.getName()).signWith(key).compact();

    return jwt;
  }

  public String getEmailFromToken(String jwt) {
    jwt = jwt.substring(7);

    Claims claim = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

    String email = String.valueOf(claim.get("email"));
    return email;
  }

}
