package com.chatlet.chatlet.repositories;

import com.chatlet.chatlet.data.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    
}
