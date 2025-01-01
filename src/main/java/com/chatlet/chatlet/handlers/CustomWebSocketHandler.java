package com.chatlet.chatlet.handlers;
import com.chatlet.chatlet.config.SessionRegistry;
import com.chatlet.chatlet.data.dtos.MessageDTO;
import com.chatlet.chatlet.services.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;
import java.io.IOException;



@RequiredArgsConstructor
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final SessionRegistry sessions;
    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {

        var principal = session.getPrincipal();

        if (principal == null || principal.getName() == null) {
            session.close(SERVER_ERROR.withReason("User must be authenticated"));
            return;
        }



        sessions.addSession(principal.getName(), session);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws UsernameNotFoundException, IOException {

        if (message.getPayload().isEmpty()) {
            session.sendMessage(new TextMessage("Message cannot be empty"));
            return;
        }

        SecurityContext securityContext = (SecurityContext) session.getAttributes().get("SECURITY_CONTEXT_WEBSOCKET");
        if (securityContext != null) {
            SecurityContextHolder.setContext(securityContext);
        }

        try {
        MessageDTO messageDTO = objectMapper.readValue(message.getPayload(), MessageDTO.class);
            messageService.sendMessage(messageDTO);
            session.sendMessage(message);
        sendMessageToUser(messageDTO.getReceiver(), message);
        } catch (UsernameNotFoundException | JsonProcessingException e) {
            session.sendMessage(new TextMessage(e.getMessage()));
        } catch (IOException e) {
            session.sendMessage(new TextMessage("Message not sent"));
        }


    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        var principal = session.getPrincipal();
        sessions.removeSession(principal.getName());
    }


    private void sendMessageToUser(String username, TextMessage message) throws IOException {
        WebSocketSession session = sessions.getSession(username);


        if (session != null && session.isOpen()) {

                session.sendMessage(message);

        }


    }


}
