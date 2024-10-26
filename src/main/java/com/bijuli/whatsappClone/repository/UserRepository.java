package com.bijuli.whatsappClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bijuli.whatsappClone.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

  public User findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.name LIKE %:query% OR u.email LIKE %:query%")
  List<User> searchUsers(@Param("query") String query);

}
