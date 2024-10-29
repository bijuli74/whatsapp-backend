package com.bijuli.whatsappClone.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.bijuli.whatsappClone.dto.SendMessageRequest;
import com.bijuli.whatsappClone.exception.ChatException;
import com.bijuli.whatsappClone.exception.MessageException;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.Chat;
import com.bijuli.whatsappClone.model.Message;
import com.bijuli.whatsappClone.model.User;
import com.bijuli.whatsappClone.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private ChatServiceImpl chatService;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @Override
  public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
    User user = userService.findUserById(req.getUserId());
    Chat chat = chatService.findChatById(req.getChatId());

    Message message = new Message();
    message.setChat(chat);
    message.setUser(user);
    message.setContent(req.getContent());
    message.setTimestamp(LocalDateTime.now());

    message = messageRepository.save(message);

    // Send message to WebSocket topic based on chat type
    if (chat.isGroup()) {
      messagingTemplate.convertAndSend("/group/" + chat.getId(), message);
    } else {
      messagingTemplate.convertAndSend("/user/" + chat.getId(), message);
    }

    return message;
  }

  @Override
  public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {

    Chat chat = chatService.findChatById(chatId);

    if (!chat.getUsers().contains(reqUser)) {
      throw new UserException("You are not related to this chat");
    }

    List<Message> messages = messageRepository.findByChatId(chat.getId());

    return messages;

  }

  @Override
  public Message findMessageById(Integer messageId) throws MessageException {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(() -> new MessageException("The required message is not found"));
    return message;
  }

  @Override
  public void deleteMessage(Integer messageId, User reqUser) throws MessageException {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(() -> new MessageException("The required message is not found"));

    if (message.getUser().getId() == reqUser.getId()) {
      messageRepository.delete(message);
    } else {
      throw new MessageException("You are not authorized for this task");
    }
  }

}