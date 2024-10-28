package com.bijuli.whatsappClone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bijuli.whatsappClone.config.JwtUtils;
import com.bijuli.whatsappClone.dto.ApiResponse;
import com.bijuli.whatsappClone.dto.AuthResponse;
import com.bijuli.whatsappClone.dto.LoginRequest;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.User;
import com.bijuli.whatsappClone.repository.UserRepository;
import com.bijuli.whatsappClone.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api")
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signUpHandler(@RequestBody User user) throws UserException {
    String name = user.getName();
    String email = user.getEmail();
    String password = user.getPassword();

    User isUser = userRepository.findByEmail(email);
    if (isUser != null) {
      throw new UserException("Email already linked to another account");
    }

    User createdUser = new User();
    createdUser.setEmail(email);
    createdUser.setName(name);
    createdUser.setPassword(passwordEncoder.encode(password));
    // createdUser.setPassword(password);

    userRepository.save(createdUser);

    Authentication authentication = authenticate(email, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.generateToken(authentication);
    AuthResponse response = new AuthResponse(jwt, true);

    return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);

  }

  @PostMapping("/signin")
  public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request) {

    String email = request.getEmail();
    String password = request.getPassword();

    Authentication authentication = authenticate(email, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.generateToken(authentication);

    AuthResponse response = new AuthResponse(jwt, true);
    return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);
  }

  public Authentication authenticate(String username, String password) {
    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

    if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid username or password");
    }

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

  }

}
