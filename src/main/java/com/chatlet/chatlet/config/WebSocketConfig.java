package com.chatlet.chatlet.config;

import com.chatlet.chatlet.handlers.CustomWebSocketHandler;
import com.chatlet.chatlet.interceptors.AuthHandshakeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final CustomWebSocketHandler customWebSocketHandler;
    private final AuthHandshakeInterceptor authHandshakeInterceptor;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(customWebSocketHandler, "/ws/message").setAllowedOrigins("*").addInterceptors(authHandshakeInterceptor);
    }
}
