package com.example.genericcoms.service;

import com.example.genericcoms.model.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WsSender {
    private static final Logger log = LoggerFactory.getLogger(WsSender.class);
    private final SimpMessagingTemplate broker;
    private final StatsService stats;

    public String send(MessageRequest req) {
        try {
            String dest = (req.getWsDestination() == null || req.getWsDestination().isBlank())
                    ? "/topic/updates" : req.getWsDestination();
            String body = req.getWsMessage() == null ? "" : req.getWsMessage();
            broker.convertAndSend(dest, body);
            stats.incWsSuccess();
            return "broadcast to '" + dest + "'";
        } catch (Exception e) {
            log.error("WebSocket send failed", e);
            stats.incWsFailure();
            return "ws send failed: " + e.getMessage();
        }
    }
}
