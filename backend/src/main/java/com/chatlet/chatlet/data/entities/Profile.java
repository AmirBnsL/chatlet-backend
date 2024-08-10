package com.chatlet.chatlet.data.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;

    private String lastname;

    private LocalDate birth;

    private Boolean gender;

    private String pictureLink;

    @OneToOne()
    private Auth auth;


}
