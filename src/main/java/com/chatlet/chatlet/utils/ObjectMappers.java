package com.chatlet.chatlet.utils;

import com.chatlet.chatlet.data.dtos.ContactDTO;
import com.chatlet.chatlet.data.dtos.MessageDTO;
import com.chatlet.chatlet.data.dtos.ProfileDto;
import com.chatlet.chatlet.data.entities.Contact;
import com.chatlet.chatlet.data.entities.Message;
import com.chatlet.chatlet.data.entities.Profile;

import java.util.Collection;

public class ObjectMappers {

    public static ProfileDto profileToDto(Profile profile) {
        return ProfileDto.builder()
                .firstname(profile.getFirstname())
                .lastname(profile.getLastname())
                .birth(profile.getBirth())
                .gender(profile.getGender())
                .pictureLink(profile.getPictureLink())
                .birth(profile.getBirth())
                .build();
    }

    public static MessageDTO messageToDTO(Message message) {
        return MessageDTO.builder()
                .message(message.getMessage())
                .type(message.getMessageType())
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .timestamp(message.getTimestamp())
                .build();

    }

    public static Collection<MessageDTO> messagesToDTOs(Collection<Message> messages) {
        return messages.stream().map(ObjectMappers::messageToDTO).toList();
    }

    public static ContactDTO contactToDTO(Contact contact) {
        return ContactDTO.builder()
                .id(contact.getId())
                .username(contact.getContactId().getUsername())
                .avatarLink(contact.getContactId().getProfile().getPictureLink())
                .build();
    }

    public static Collection<ContactDTO> contactsToDTOs(Collection<Contact> contacts) {
        return contacts.stream().map(ObjectMappers::contactToDTO).toList();
    }
    



    
}
