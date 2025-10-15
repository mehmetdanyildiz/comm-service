package com.example.genericcoms.service;

import com.example.genericcoms.config.RabbitConfig;
import com.example.genericcoms.model.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitSender {
    private static final Logger log = LoggerFactory.getLogger(RabbitSender.class);
    private final RabbitTemplate rabbitTemplate;
    private final StatsService stats;

    public String send(MessageRequest req) {
        try {
            String exchange = req.getRabbitExchange() == null ? "" : req.getRabbitExchange();
            String routingKey = req.getRabbitRoutingKey() == null || req.getRabbitRoutingKey().isBlank()
                    ? RabbitConfig.DEFAULT_QUEUE : req.getRabbitRoutingKey();
            String payload = req.getRabbitMessage() == null ? "" : req.getRabbitMessage();
            rabbitTemplate.convertAndSend(exchange, routingKey, payload);
            stats.incRabbitSuccess();
            return "sent to exchange='" + exchange + "', routingKey='" + routingKey + "'";
        } catch (Exception e) {
            log.error("Rabbit send failed", e);
            stats.incRabbitFailure();
            return "rabbit send failed: " + e.getMessage();
        }
    }
}
