package org.example.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaLLMConfig {
    @Resource
    private ChatModel chatModel;

    @Bean
    public ChatClient openAiChatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

}
