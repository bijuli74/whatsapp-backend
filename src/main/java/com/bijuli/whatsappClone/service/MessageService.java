package com.bijuli.whatsappClone.service;

import java.util.List;
import java.util.UUID;

import com.bijuli.whatsappClone.dto.SendMessageRequest;
import com.bijuli.whatsappClone.exception.ChatException;
import com.bijuli.whatsappClone.exception.MessageException;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.Message;
import com.bijuli.whatsappClone.model.User;

public interface MessageService {

  public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;

  public List<Message> getChatsMessages(UUID chatId, User reqUser) throws ChatException, UserException;

  public Message findMessageById(UUID messageId) throws MessageException;

  public void deleteMessage(UUID messageId, User reqUser) throws MessageException;
}
