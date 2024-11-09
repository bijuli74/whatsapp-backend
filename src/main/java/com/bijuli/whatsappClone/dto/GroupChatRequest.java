package com.bijuli.whatsappClone.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatRequest {

  private List<UUID> userIds;
  private String chatName;
  private String chatImage;
}
