package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-30 12:21
 * @Description 知识出处：
 * https://docs.spring.io/spring-ai/reference/api/retrieval-augmented-generation.html#_advanced_rag
 */
@Slf4j
@RestController
public class RagController
{
    @Resource(name = "qwenChatClient")
    private ChatClient chatClient;
    @Resource
    private VectorStore vectorStore;

    /**
     * http://localhost:8012/rag4aiops?msg=00000
     * http://localhost:8012/rag4aiops?msg=C2222
     * @param msg
     * @return
     */
    @GetMapping("/rag4aiops")
    public Flux<String> rag(String msg)
    {
        String systemInfo = """
                你是一个运维工程师,按照给出的编码给出对应故障解释,否则回复找不到信息。
                """;

        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build())
                .build();
        
        // 构建 Prompt 并打印内容(用于调试)
        Prompt prompt = new Prompt(
                org.springframework.ai.chat.messages.UserMessage.builder().text(msg).build(),
                org.springframework.ai.chat.messages.SystemMessage.builder().text(systemInfo).build()
        );
        
        log.info("========== Prompt Debug Info (Before RAG) ==========");
        log.info("Messages count: {}", prompt.getInstructions().size());
        for (Message message : prompt.getInstructions()) {
            log.info("Message Type: {}", message.getMessageType());
            log.info("Message Content: {}", message.getText());
            log.info("----------------------------------------");
        }
        log.info("====================================================");

        return chatClient
                .prompt(prompt)
                .advisors(advisor)
                .stream()
                .content();
    }
    
}
