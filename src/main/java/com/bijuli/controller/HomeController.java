package com.bijuli.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HomeController {

  @GetMapping("/")
  public ResponseEntity<String> home() {
    return new ResponseEntity<String>("Welcome to WhatsApp!", HttpStatus.OK);
  }
}
