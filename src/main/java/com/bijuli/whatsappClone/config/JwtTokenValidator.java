package com.bijuli.whatsappClone.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String jwt = authHeader.substring(7);

      SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
      // Claims claim =
      // Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
      // // old

      // new
      Claims claim = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

      /**
       * jwt = Jwts.parser() // (1)
       * .keyLocator(keyLocator) // (2) dynamically locate signing or encryption keys
       * .verifyWith(key) // or a constant key used to verify all signed JWTs
       * .decryptWith(key) // or a constant key used to decrypt all encrypted JWTs
       * .build() // (3)
       * .parse(compact); // (4) or parseSignedClaims, parseEncryptedClaims,
       * parseSignedContent, etc
       **/

      String username = String.valueOf(claim.get("username"));
      String authorities = String.valueOf(claim.get("authorities"));

      List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

      Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, auths);

      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (Exception e) {
      throw new BadCredentialsException("invalid JWT token received ...");
    }

    filterChain.doFilter(request, response);
  }

}
