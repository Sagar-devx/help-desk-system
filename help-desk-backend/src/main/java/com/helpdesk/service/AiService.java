package com.helpdesk.service;

import com.helpdesk.tools.TicketDatabaseTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final TicketDatabaseTool ticketDatabaseTool;

    @Value("classpath:helpdesk-system.st")
    private Resource systemPromptResource;

    public String getResponseFromAssistant(String query)
    {
        return this.chatClient
                .prompt()
                .tools(ticketDatabaseTool)
                .system(systemPromptResource)
                .user(query)
                .call()
                .content();
    }
}
