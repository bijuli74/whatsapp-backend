package com.bijuli.whatsappClone.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatRequest {

  private List<Integer> userIds;
  private String chatName;
  private String chatImage;
}
