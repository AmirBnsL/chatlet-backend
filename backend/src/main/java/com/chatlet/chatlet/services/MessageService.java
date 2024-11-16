package com.chatlet.chatlet.services;

import com.chatlet.chatlet.data.dtos.MessageDTO;
import com.chatlet.chatlet.data.entities.Auth;
import com.chatlet.chatlet.data.entities.Contact;
import com.chatlet.chatlet.data.entities.Message;
import com.chatlet.chatlet.data.securityEntities.SecurityUser;
import com.chatlet.chatlet.repositories.AuthRepository;
import com.chatlet.chatlet.repositories.ContactRepository;
import com.chatlet.chatlet.repositories.MessageRepository;
import com.chatlet.chatlet.utils.ObjectMappers;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final AuthRepository authRepository;
    private final ContactRepository contactRepository;

    @Transactional
    public void sendMessage(MessageDTO message) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Auth senderEntity = authRepository.findByUsername(message.getSender()).orElseThrow(()-> new UsernameNotFoundException("Sender not found"));
        Auth receiverEntity = authRepository.findByUsername(message.getReceiver()).orElseThrow(()-> new UsernameNotFoundException("Receiver not found"));

        Contact contact = contactRepository.findByUserIdAndContactIdAndIsFriend(securityUser.getAuth(),receiverEntity,true).orElseThrow(()-> new RuntimeException("You are not friends with this user"));



        Message messageEntity = new Message();
        messageEntity.setMessageType(message.getType());
        messageEntity.setMessage(message.getMessage());
        messageEntity.setContact(contact);
        messageEntity.setTimestamp(Timestamp.from(Instant.now()));
        messageEntity.setSender(securityUser.getAuth().getUsername());
        messageEntity.setReceiver(receiverEntity.getUsername());
        contact.getMessages().add(messageEntity);
        messageRepository.save(messageEntity);
        contactRepository.save(contact);



    }


    @Transactional
    public Collection<MessageDTO> getMessages(String friendName)  {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Auth friend = authRepository.findByUsername(friendName).orElseThrow(()-> new UsernameNotFoundException("Friend not found"));

        Contact contact = contactRepository.findByUserIdAndContactIdAndIsFriend(securityUser.getAuth(),friend,true).orElseThrow(()-> new RuntimeException("You are not friends with this user"));

        Collection<Message> messages = contact.getMessages();
        return ObjectMappers.messagesToDTOs(messages);



    }


}
