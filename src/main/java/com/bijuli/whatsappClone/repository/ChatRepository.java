package com.bijuli.whatsappClone.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bijuli.whatsappClone.model.Chat;
import com.bijuli.whatsappClone.model.User;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

  @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id=:userId")
  public List<Chat> findChatByUserId(@Param("userId") UUID userId);

  @Query("SELECT c FROM Chat c WHERE c.isGroup=false AND :user MEMBER OF c.users AND :reqUser MEMBER OF c.users")
  public Chat findSingleChatByUserIds(@Param("user") User user, @Param("reqUser") User reqUser);
}
