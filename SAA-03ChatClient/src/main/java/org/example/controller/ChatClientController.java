package org.example.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatClientController {
    @Resource
    private ChatClient chatClient;

    @GetMapping("/chatclient/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue = "2加9等于几") String msg)
    {
        return chatClient.prompt().user(msg).call().content();
    }

}
