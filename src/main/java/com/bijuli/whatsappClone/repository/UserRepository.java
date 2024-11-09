package com.bijuli.whatsappClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bijuli.whatsappClone.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  public User findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.name LIKE %:query% OR u.email LIKE %:query%")
  List<User> searchUser(@Param("query") String query);

}
