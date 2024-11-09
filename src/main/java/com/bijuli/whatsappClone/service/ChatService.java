package com.bijuli.whatsappClone.service;

import java.util.List;
import java.util.UUID;

import com.bijuli.whatsappClone.dto.GroupChatRequest;
import com.bijuli.whatsappClone.exception.ChatException;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.Chat;
import com.bijuli.whatsappClone.model.User;

public interface ChatService {

  public Chat createChat(User reqUser, UUID userId) throws UserException;

  public Chat findChatById(UUID chatId) throws ChatException;

  public List<Chat> findAllChatByUserId(UUID userId) throws UserException;

  public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;

  public Chat addUserToGroup(UUID userId, UUID chatId, User reqUser) throws UserException, ChatException;

  public Chat renameGroup(UUID chatId, String groupName, User reqUser) throws ChatException, UserException;

  public Chat removeFromGroup(UUID chatId, UUID userId, User reqUser) throws UserException, ChatException;

  public void deleteChat(UUID chatId, UUID userId) throws ChatException, UserException;

}