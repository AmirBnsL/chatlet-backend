package com.chatlet.chatlet.data.dtos;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
public class MessageDTO {
    private String message;
    private String type;
    private String sender;
    private String receiver;
    private Timestamp timestamp;
}
