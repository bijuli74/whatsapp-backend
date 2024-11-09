package com.bijuli.whatsappClone.service;

import java.util.List;
import java.util.UUID;

import com.bijuli.whatsappClone.dto.UpdateUserRequest;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.User;

public interface UserService {

  public User findUserById(UUID Id) throws UserException;

  public User findUserProfile(String jwt) throws UserException;

  public User updateUser(UUID Id, UpdateUserRequest req) throws UserException;

  public List<User> searchUser(String query);
}
