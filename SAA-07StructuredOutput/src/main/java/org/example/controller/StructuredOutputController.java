package org.example.controller;

import jakarta.annotation.Resource;
import org.example.records.StudentRecord;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.function.Consumer;

@RestController
public class StructuredOutputController {
    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    @GetMapping("/structuredoutput/chat")
    public StudentRecord chat(@RequestParam(name = "sname") String sname,
                              @RequestParam(name = "email") String email) {

        return qwenChatClient.prompt().user(promptUserSpec ->
                promptUserSpec.text("学生姓名为" + sname + "，学号1001，邮箱为" + email + "专业为计算机科学与技术")
                        .param("sname", sname).param("email", email))
                .call()
                .entity(StudentRecord.class);

    }

    @GetMapping("/structuredoutput/chat2")
    public StudentRecord chat2(@RequestParam(name = "sname") String sname,
                              @RequestParam(name = "email") String email) {
        PromptTemplate promptTemplate = new PromptTemplate("学生姓名为{sname}，学号1002，邮箱为{email}专业为计算机科学与技术");
        Prompt prompt = promptTemplate.create(Map.of("sname", sname, "email", email));

        return qwenChatClient.prompt(prompt).call().entity(StudentRecord.class);
    }

}
