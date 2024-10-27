package com.bijuli.whatsappClone.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  public long getJwtExpiration() {
    return jwtExpiration;
  }

  SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  public String generateToken(Authentication authentication) {

    String jwt = Jwts.builder()
        .subject("somb")
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + jwtExpiration))
        .claim("email", authentication.getName())
        .signWith(key)
        .compact();

    return jwt;
  }

  public String getEmailFromToken(String jwt) {
    jwt = jwt.substring(7);

    Claims claim = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

    String email = String.valueOf(claim.get("email"));
    return email;
  }

}
