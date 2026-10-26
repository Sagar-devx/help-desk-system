package com.helpdesk.controller;

import com.helpdesk.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping
    public ResponseEntity<String> getResponse(@RequestBody String query)
    {
        return ResponseEntity.ok(aiService.getResponseFromAssistant(query));
    }

}
