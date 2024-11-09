package com.bijuli.whatsappClone.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bijuli.whatsappClone.dto.ApiResponse;
import com.bijuli.whatsappClone.dto.SendMessageRequest;
import com.bijuli.whatsappClone.exception.ChatException;
import com.bijuli.whatsappClone.exception.MessageException;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.Message;
import com.bijuli.whatsappClone.model.User;
import com.bijuli.whatsappClone.service.MessageServiceImpl;
import com.bijuli.whatsappClone.service.UserServiceImpl;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  @Autowired
  private MessageServiceImpl messageService;

  @Autowired
  private UserServiceImpl userService;

  @PostMapping("/create")
  public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest sendMessageRequest,
      @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

    User user = this.userService.findUserProfile(jwt);

    sendMessageRequest.setUserId(user.getId());
    Message message = this.messageService.sendMessage(sendMessageRequest);

    return new ResponseEntity<Message>(message, HttpStatus.OK);
  }

  @GetMapping("/{chatId}")
  public ResponseEntity<List<Message>> getChatMessageHandler(@PathVariable UUID chatId,
      @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

    User user = this.userService.findUserProfile(jwt);
    List<Message> messages = this.messageService.getChatsMessages(chatId, user);

    return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
  }

  @DeleteMapping("/{messageId}")
  public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable UUID messageId,
      @RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException {

    User user = this.userService.findUserProfile(jwt);
    this.messageService.deleteMessage(messageId, user);

    ApiResponse res = new ApiResponse("Deleted successfully....", false);

    return new ResponseEntity<>(res, HttpStatus.OK);
  }

}
