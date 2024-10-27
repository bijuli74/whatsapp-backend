package com.bijuli.whatsappClone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bijuli.whatsappClone.dto.ApiResponse;
import com.bijuli.whatsappClone.dto.UpdateUserRequest;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.User;
import com.bijuli.whatsappClone.service.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private UserServiceImpl userService;

  @GetMapping("/profile")
  public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token)
      throws UserException {

    User user = this.userService.findUserProfile(token);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @GetMapping("/{query}")
  public ResponseEntity<List<User>> searchUserHandler(@RequestHeader("query") String query) {

    List<User> users = this.userService.searchUser(query);
    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
  }

  @PostMapping("/update")
  public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest request,
      @RequestHeader("Authorization") String token)
      throws UserException {

    User user = this.userService.findUserProfile(token);
    this.userService.updateUser(user.getId(), request);

    ApiResponse response = new ApiResponse();
    response.setMessage("User updated successfully");
    response.setStatus(true);

    return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
  }

}
