package com.chatlet.chatlet.data.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProfileDto {


    private String firstname;

    private String lastname;

    private LocalDate birth;

    private Boolean gender;

    private String pictureLink;


}
