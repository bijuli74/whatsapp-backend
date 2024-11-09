package com.bijuli.whatsappClone.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

  private UUID userId;
  private UUID chatId;
  private String content;

}
