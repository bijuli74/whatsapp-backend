package com.bijuli.whatsappClone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.bijuli.whatsappClone.config.JwtUtils;
import com.bijuli.whatsappClone.dto.UpdateUserRequest;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.User;
import com.bijuli.whatsappClone.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public User findUserById(Integer id) throws UserException {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserException("The requested user is not found"));
  }

  @Override
  public User findUserProfile(String jwt) throws UserException {
    String email = jwtUtils.getEmailFromToken(jwt);
    if (email == null) {
      throw new BadCredentialsException("Recieved invalid token...");
    }

    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UserException("User not found with the provided email ");
    }

    return user;
  }

  @Override
  public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
    User user = findUserById(userId);

    if (req.getName() != null) {
      user.setName(req.getName());
    }
    if (req.getProfile() != null) {
      user.setProfile(req.getProfile());
    }
    return userRepository.save(user);
  }

  @Override
  public List<User> searchUser(String query) {
    List<User> users = userRepository.searchUser(query);
    return users;
  }
}
