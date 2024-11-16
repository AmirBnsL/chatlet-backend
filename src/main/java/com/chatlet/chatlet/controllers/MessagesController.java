package com.chatlet.chatlet.controllers;


import com.chatlet.chatlet.data.dtos.MessageDTO;
import com.chatlet.chatlet.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@AllArgsConstructor
@RestController
public class MessagesController {

    private final MessageService messageService;

    @GetMapping("/messages")
    public Collection<MessageDTO> getMessages(String friendName) {
        return messageService.getMessages(friendName);
    }
 }
