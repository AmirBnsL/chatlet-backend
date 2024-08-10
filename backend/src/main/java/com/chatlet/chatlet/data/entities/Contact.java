package com.chatlet.chatlet.data.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   @OneToOne
    private Auth contactId;

   @ManyToOne
    private  Auth userId;



   private Timestamp createdAt;

   private Timestamp updatedAt;



   @OneToMany
    private Collection<Message> messages;





}
