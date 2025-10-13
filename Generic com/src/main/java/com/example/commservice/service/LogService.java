package com.example.commservice.service;

import com.example.commservice.model.*;
import com.example.commservice.repository.MessageLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {
    private final MessageLogRepository repo;

    public void log(MessageChannel channel, MessageDirection direction, MessageStatus status,
                    String target, String payload, String responseSummary) {
        var log = MessageLog.builder()
                .channel(channel)
                .direction(direction)
                .status(status)
                .target(target)
                .payload(payload)
                .responseSummary(responseSummary)
                .build();
        repo.save(log);
    }
}
