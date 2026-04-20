package org.example.controller;

import jakarta.annotation.Resource;
import org.example.utils.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ToolCallingController {
    @Resource
    private ChatClient chatClient;

    @GetMapping("/toolcall/chat")
    public Flux<String> chat(@RequestParam(name = "msg",defaultValue = "你是谁，现在几点") String msg)
    {
        return chatClient.prompt()
                .user( msg)
                .tools(new DateTimeTools())
                .stream()
                .content();
    }
}
