package com.example.commservice.controller;

import com.example.commservice.dto.*;
import com.example.commservice.model.MessageLog;
import com.example.commservice.repository.MessageLogRepository;
import com.example.commservice.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Communication")
public class MessageController {

    private final HttpRelayService httpRelayService;
    private final RabbitPublisherService rabbitPublisherService;
    private final WsPushService wsPushService;
    private final MessageLogRepository logRepository;

    @PostMapping("/send/http")
    @Operation(summary = "HTTP isteği relay et ve logla")
    public ResponseEntity<String> sendHttp(@Valid @RequestBody SendHttpRequest req) {
        return httpRelayService.relay(req);
    }

    @PostMapping("/send/rabbit")
    @Operation(summary = "RabbitMQ mesajı yayınla ve logla")
    public ResponseEntity<String> sendRabbit(@Valid @RequestBody SendRabbitRequest req) {
        String result = rabbitPublisherService.publish(req);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/send/ws")
    @Operation(summary = "WebSocket (STOMP) mesajı gönder ve logla")
    public ResponseEntity<String> sendWs(@Valid @RequestBody SendWsRequest req) {
        String result = wsPushService.push(req.destination(), req.payload());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/logs")
    @Operation(summary = "Log kayıtlarını listele")
    public List<MessageLog> logs() {
        return logRepository.findAll();
    }
}
