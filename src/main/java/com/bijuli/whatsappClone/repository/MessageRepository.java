package com.bijuli.whatsappClone.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bijuli.whatsappClone.model.Message;

public interface MessageRepository extends JpaRepository<Message, UUID> {

  @Query("SELECT m FROM Message m JOIN m.chat c WHERE c.id=:chatId")
  public List<Message> findByChatId(@Param("chatId") UUID chatId);

}
