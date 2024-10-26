package com.bijuli.whatsappClone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

  @Id
  private int Id;

  private String chatName;
  private String chatImage;
  private boolean isGroup;

  @ManyToOne
  private User createdBy;

}
