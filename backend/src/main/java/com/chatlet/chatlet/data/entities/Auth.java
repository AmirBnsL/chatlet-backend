package com.chatlet.chatlet.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(unique = true)
    private String username;



    private String password;


    private String email;

    private Boolean isDisabled;


    @OneToOne(mappedBy = "auth")
    private Profile profile;

    @OneToMany()
    private Collection<Message> messages;


}
