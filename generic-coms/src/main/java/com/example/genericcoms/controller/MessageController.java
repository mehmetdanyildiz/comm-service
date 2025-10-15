package com.example.genericcoms.controller;

import com.example.genericcoms.model.MessageRequest;
import com.example.genericcoms.service.HttpSender;
import com.example.genericcoms.service.RabbitSender;
import com.example.genericcoms.service.StatsService;
import com.example.genericcoms.service.WsSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MessageController {

    private final HttpSender httpSender;
    private final RabbitSender rabbitSender;
    private final WsSender wsSender;
    private final StatsService stats;

    @PostMapping("/messages")
    public ResponseEntity<?> send(@Valid @RequestBody MessageRequest req) {
        return switch (req.getChannel()) {
            case HTTP -> httpSender.send(req);
            case RABBIT -> ResponseEntity.ok(rabbitSender.send(req));
            case WS -> ResponseEntity.ok(wsSender.send(req));
        };
    }

    @GetMapping("/stats")
    public Object stats() {
        return stats.getSnapshot();
    }
}
