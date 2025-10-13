package com.example.commservice.service;

import com.example.commservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WsPushService {
    private final SimpMessagingTemplate messagingTemplate;
    private final LogService logService;

    public String push(String destination, String payload) {
        try {
            messagingTemplate.convertAndSend(destination, payload);
            logService.log(MessageChannel.WEBSOCKET, MessageDirection.OUTBOUND, MessageStatus.SUCCESS,
                    destination, payload, "WS SENT");
            return "WS message sent";
        } catch (Exception ex) {
            logService.log(MessageChannel.WEBSOCKET, MessageDirection.OUTBOUND, MessageStatus.FAILED,
                    destination, payload, ex.getClass().getSimpleName());
            return "WS send failed: " + ex.getMessage();
        }
    }
}
