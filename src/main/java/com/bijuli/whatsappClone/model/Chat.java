package com.bijuli.whatsappClone.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int id;

  @Column(name = "chat_name", nullable = false)
  private String chatName;

  @Column(name = "chat_image")
  private String chatImage;

  @Column(name = "is_group")
  private boolean isGroup;

  @ManyToOne
  private User createdBy;

  @ManyToMany
  private Set<User> users = new HashSet<>();

  @OneToMany
  private List<Message> messages = new ArrayList<>();

  // @ManyToMany
  // private Set<User> user;

  // @OneToMany
  // private List<Message> message;

}
