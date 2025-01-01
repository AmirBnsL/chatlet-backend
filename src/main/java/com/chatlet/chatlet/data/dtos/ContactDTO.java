package com.chatlet.chatlet.data.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDTO {
    private Long id;

    private String username;

    private String avatarLink;
    
}
