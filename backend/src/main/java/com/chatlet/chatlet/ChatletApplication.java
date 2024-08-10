package com.chatlet.chatlet;

import com.chatlet.chatlet.config.RsaKeyProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@OpenAPIDefinition
@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class ChatletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatletApplication.class, args);
    }

}
