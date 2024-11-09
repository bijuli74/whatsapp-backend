package com.bijuli.whatsappClone.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleChatRequest {

  private UUID userId;
}
