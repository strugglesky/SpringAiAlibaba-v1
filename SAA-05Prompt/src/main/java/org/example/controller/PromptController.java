package org.example.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class PromptController {
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;
    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;
    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    @GetMapping("/prompt/chat")
    public Flux<ChatResponse> chat(@RequestParam(name = "question",defaultValue = "你是谁") String question)
    {
        // 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个善于讲故事的助手,我向你提供一些关键词,你来进行故事的生成并且回复，字数控制在500字以内,回复格式:html");

        // 用户消息
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);

        return deepseekChatModel.stream(prompt);

    }

    /**
     * 1. 通过deepseek ChatModel实现prompt
     * @param question
     * @return Flux<String>
     */
    @GetMapping("prompt/chat1")
    public Flux<String> chat1(@RequestParam(name = "question",defaultValue = "你是谁") String question)
    {
        SystemMessage systemMessage = new SystemMessage("你是一个专业法律助手，只回复与法律相关的问题，对于其他与法律无关的问题，回复:此问题与法律无关，我无法回复，请重新提问。");
        UserMessage userMessage = new UserMessage(question);
        return deepseekChatModel.stream(systemMessage,userMessage);
    }
    /**
     * 2. 通过qwen ChatModel实现prompt
     * @param question
     * @return Flux<String>
     */
    @GetMapping("prompt/chat2")
    public Flux<String> chat2(@RequestParam(name = "question",defaultValue = "你是谁") String question)
    {
        SystemMessage systemMessage = new SystemMessage("你是一个专业法律助手，只回复与法律相关的问题，对于其他与法律无关的问题，回复:此问题与法律无关，我无法回复，请重新提问。");
        UserMessage userMessage = new UserMessage(question);
        return qwenChatModel.stream(systemMessage,userMessage);
    }
    @GetMapping("prompt/chat3")
    public Flux<String> chat3(@RequestParam(name = "question",defaultValue = "你是谁") String question)
    {
        return deepseekChatClient.prompt()
                .system("你是一个专业法律助手，只回复与法律相关的问题，对于其他与法律无关的问题，回复:此问题与法律无关，我无法回复，请重新提问。")
                .user( question)
                .stream()
                .content();

    }
    @GetMapping("/prompt/chat4")
    public Flux<String> chat4(String question)
    {
        // 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手," +
                "每个故事控制在600字以内且以HTML格式返回");

        // 用户消息
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);

        return deepseekChatModel.stream( prompt)
                .map(response -> response.getResults().get(0).getOutput().getText());

    }

    @GetMapping("/prompt/chat5")
    public String chat5(String question)
    {
//        AssistantMessage assistantMessage = deepseekChatClient.prompt()
//                .user(question)
//                .call()
//                .chatResponse()
//                .getResult()
//                .getOutput();
//
//        return assistantMessage.getText();

        AssistantMessage assistantMessage = deepseekChatClient.prompt()
                .user(question)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();


        return assistantMessage.getText();
    }

    @GetMapping("/prompt/chat6")
    public String chat6(String city)
    {

        String answer = deepseekChatClient.prompt()
                .user(city + "未来3天天气情况如何?")
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();

        ToolResponseMessage toolResponseMessage =
                new ToolResponseMessage(
                        List.of(new ToolResponseMessage.ToolResponse("1","获得天气",city + "的天气是晴天，温度25℃")
                        )
                );

        String toolResponse = toolResponseMessage.getText();

        return answer + toolResponse;
    }
}
