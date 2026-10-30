package com.helpdesk.service;

import com.helpdesk.tools.TicketDatabaseTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final TicketDatabaseTool ticketDatabaseTool;

    @Value("classpath:helpdesk-system.st")
    private Resource systemPromptResource;

    public String getResponseFromAssistant(String query, String conversationId)
    {
        return this.chatClient
                .prompt()
                .tools(ticketDatabaseTool)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,conversationId))
                .system(systemPromptResource)
                .user(query)
                .call()
                .content();
    }

    public Flux<String> getStreamResponseFromAssistant(String query, String conversationId)
    {
        return this.chatClient
                .prompt()
                .tools(ticketDatabaseTool)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,conversationId))
                .system(systemPromptResource)
                .user(query)
                .stream()
                .content();
    }
}
