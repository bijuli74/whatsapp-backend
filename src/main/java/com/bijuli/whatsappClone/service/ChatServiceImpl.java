package com.bijuli.whatsappClone.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bijuli.whatsappClone.dto.GroupChatRequest;
import com.bijuli.whatsappClone.exception.ChatException;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.Chat;
import com.bijuli.whatsappClone.model.User;
import com.bijuli.whatsappClone.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private ChatRepository chatRepository;

  @Override
  public Chat createChat(User reqUser, UUID userId) throws UserException {

    User user = userService.findUserById(userId);
    Chat isChatExist = chatRepository.findSingleChatByUserIds(user, reqUser);

    System.out.println(isChatExist);
    if (isChatExist != null) {
      return isChatExist;
    }

    Chat chat = new Chat();
    chat.setChatName(reqUser.getName() + "&" + user.getName());
    chat.setCreatedBy(reqUser);
    chat.getUsers().add(user);
    chat.getUsers().add(reqUser);
    chat.setGroup(false);

    chat = chatRepository.save(chat);
    return chat;
  }

  @Override
  public Chat findChatById(UUID chatId) throws ChatException {

    return chatRepository.findById(chatId)
        .orElseThrow(() -> new ChatException("Chat not found"));
  }

  @Override
  public List<Chat> findAllChatByUserId(UUID userId) throws UserException {

    User user = userService.findUserById(userId);

    List<Chat> chats = this.chatRepository.findChatByUserId(user.getId());
    return chats;
  }

  @Override
  public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {

    Chat group = new Chat();
    group.setGroup(true);
    group.setChatImage(req.getChatImage());
    group.setChatName(req.getChatName());
    group.setCreatedBy(reqUser);
    group.getAdmins().add(reqUser);

    for (UUID userId : req.getUserIds()) {
      User user = userService.findUserById(userId);
      group.getUsers().add(user);
    }

    group = chatRepository.save(group);
    return group;
  }

  @Override
  public Chat addUserToGroup(UUID userId, UUID chatId, User reqUser) throws UserException, ChatException {

    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new ChatException("Chat not found"));

    User user = userService.findUserById(userId);

    if (chat.getAdmins().contains(reqUser)) {
      chat.getUsers().add(user);
      return chat;
    } else {
      throw new UserException("Access Denied to add user");
    }

  }

  @Override
  public Chat renameGroup(UUID chatId, String groupName, User reqUser) throws ChatException, UserException {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new ChatException("Chat not found"));

    if (chat.getUsers().contains(reqUser)) {
      chat.setChatName(groupName);
      return chatRepository.save(chat);
    } else {
      throw new UserException("You are not the user");
    }
  }

  @Override
  public Chat removeFromGroup(UUID chatId, UUID userId, User reqUser) throws UserException, ChatException {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new ChatException("Chat not found"));

    User user = userService.findUserById(userId);
    if (chat.getAdmins().contains(reqUser)) {

      chat.getUsers().remove(user);
      return chat;
    } else if (chat.getUsers().contains(reqUser)) {

      if (user.getId() == reqUser.getId()) {
        chat.getUsers().remove(user);
        return chatRepository.save(chat);
      }
    }
    throw new UserException("Access Denied to remove user");
  }

  @Override
  public void deleteChat(UUID chatId, UUID userId) throws ChatException, UserException {

    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new ChatException("Chat not found"));

    chatRepository.delete(chat);
  }

}