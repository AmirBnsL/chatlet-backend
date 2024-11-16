package com.chatlet.chatlet.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Check(constraints = "sender <> receiver")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contact contact;


    private String sender;
    private String receiver;

    private String messageType;

    private String message;

    private Timestamp timestamp;

    
}
