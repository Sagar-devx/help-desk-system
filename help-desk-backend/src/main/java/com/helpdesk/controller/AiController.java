package com.helpdesk.controller;

import com.helpdesk.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AiController {

    private final AiService aiService;

    @PostMapping
    public ResponseEntity<String> getResponse(
            @RequestBody String query,
            @RequestHeader(value = "ConversationId", required = false, defaultValue = "default") String conversationId) {
        return ResponseEntity.ok(aiService.getResponseFromAssistant(query, conversationId));
    }

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamResponse(
            @RequestBody String query,
            @RequestHeader(value = "ConversationId", required = false, defaultValue = "default") String conversationId) {
        return aiService.getStreamResponseFromAssistant(query, conversationId);
    }
}
