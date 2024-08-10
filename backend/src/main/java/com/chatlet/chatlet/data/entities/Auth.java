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



    private String username;



    private String password;


    private String email;

    private Boolean isDisabled;


    @OneToOne()
    private Profile profile;


    @OneToMany
    private Collection<Contact> contacts;
}
