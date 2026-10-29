package com.helpdesk.controller;

import com.helpdesk.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping
    public ResponseEntity<String> getResponse(@RequestBody String query,
                                              @RequestHeader("ConversationId")String conversationId)
    {
        return ResponseEntity.ok(aiService.getResponseFromAssistant(query,conversationId));
    }

    @GetMapping("/stream")
    public Flux<String> streamResponse(@RequestBody String query,
                                       @RequestHeader("ConversationId")String conversationId)
    {
       return aiService.getStreamResponseFromAssistant(query,conversationId);
    }



}
