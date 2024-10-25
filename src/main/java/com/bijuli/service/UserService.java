package com.bijuli.service;

import com.bijuli.dto.UpdateUserRequest;
import com.bijuli.exception.UserException;
import com.bijuli.model.User;

import java.util.List;

public interface UserService {

  public User findById(Integer Id) throws UserException;

  public User findUserProfile(String jwt) throws UserException;

  public User updateUser(Integer Id, UpdateUserRequest req) throws UserException;

  public List<User> searchUser(String query);
}
