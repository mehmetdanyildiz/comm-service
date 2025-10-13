package com.example.commservice.service;

import com.example.commservice.config.RabbitConfig;
import com.example.commservice.dto.SendRabbitRequest;
import com.example.commservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitPublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final LogService logService;

    public String publish(SendRabbitRequest req) {
        String exchange = (req.exchange() == null || req.exchange().isBlank()) ? RabbitConfig.EXCHANGE_NAME : req.exchange();
        String routingKey = (req.routingKey() == null || req.routingKey().isBlank()) ? RabbitConfig.ROUTING_KEY : req.routingKey();

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, req.payload());
            logService.log(MessageChannel.RABBITMQ, MessageDirection.OUTBOUND, MessageStatus.SUCCESS,
                    exchange + ":" + routingKey, req.payload(), "PUBLISHED");
            return "Message published";
        } catch (AmqpException ex) {
            logService.log(MessageChannel.RABBITMQ, MessageDirection.OUTBOUND, MessageStatus.FAILED,
                    exchange + ":" + routingKey, req.payload(), ex.getClass().getSimpleName());
            return "Publish failed: " + ex.getMessage();
        }
    }
}
