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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bijuli.whatsappClone.dto.ApiResponse;
import com.bijuli.whatsappClone.dto.GroupChatRequest;
import com.bijuli.whatsappClone.dto.SingleChatRequest;
import com.bijuli.whatsappClone.exception.ChatException;
import com.bijuli.whatsappClone.exception.UserException;
import com.bijuli.whatsappClone.model.Chat;
import com.bijuli.whatsappClone.model.User;
import com.bijuli.whatsappClone.service.ChatServiceImpl;
import com.bijuli.whatsappClone.service.UserServiceImpl;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

  @Autowired
  private ChatServiceImpl chatService;

  @Autowired
  private UserServiceImpl userService;

  @PostMapping("/single")
  public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,
      @RequestHeader("Authorization") String jwt) throws UserException {

    User reqUser = userService.findUserProfile(jwt);

    Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());

    return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
  }

  @PostMapping("/group")
  public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest,
      @RequestHeader("Authorization") String jwt) throws UserException {

    System.out.println(groupChatRequest);
    User reqUser = userService.findUserProfile(jwt);

    Chat chat = chatService.createGroup(groupChatRequest, reqUser);
    return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
  }

  @GetMapping("/{chatId}")
  public ResponseEntity<Chat> findChatByIdHandler(@PathVariable UUID chatId) throws ChatException {

    Chat chat = chatService.findChatById(chatId);
    return new ResponseEntity<Chat>(chat, HttpStatus.OK);
  }

  @GetMapping("/user")
  public ResponseEntity<List<Chat>> findChatByUserIdHandler(@RequestHeader("Authorization") String jwt)
      throws UserException {

    User reqUser = userService.findUserProfile(jwt);

    List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
    return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
  }

  @PutMapping("/{chatId}/add/{userId}")
  public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable UUID chatId,
      @PathVariable UUID userId, @RequestHeader("Authorization") String jwt)
      throws UserException, ChatException {

    User reqUser = userService.findUserProfile(jwt);

    Chat chat = chatService.addUserToGroup(userId, chatId, reqUser);
    return new ResponseEntity<>(chat, HttpStatus.OK);
  }

  @PutMapping("/{chatId}/remove/{userId}")
  public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable UUID chatId,
      @PathVariable UUID userId, @RequestHeader("Authorization") String jwt)
      throws UserException, ChatException {

    User reqUser = userService.findUserProfile(jwt);

    Chat chat = chatService.removeFromGroup(userId, chatId, reqUser);
    return new ResponseEntity<>(chat, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{chatId}")
  public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable UUID chatId,
      @RequestHeader("Authorization") String jwt)
      throws UserException, ChatException {

    User reqUser = userService.findUserProfile(jwt);

    chatService.deleteChat(chatId, reqUser.getId());

    ApiResponse res = new ApiResponse("Deleted Successfully...", false);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

}