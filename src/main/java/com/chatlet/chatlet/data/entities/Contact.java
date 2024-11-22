package com.chatlet.chatlet.data.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;


//how to handle the friendship status

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "contact_id"}))
@Check(constraints = "user_Id <> contact_id")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
   private Auth userId;



   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private  Auth contactId;

   private Boolean isBlocked;
   
   private Boolean isFriend;

   private Timestamp createdAt;

   private Timestamp updatedAt;

   @OneToMany(fetch = FetchType.EAGER)
   private Collection<Message> messages = new ArrayList<>();



}
