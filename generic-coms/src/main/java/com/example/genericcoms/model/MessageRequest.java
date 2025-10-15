package com.example.genericcoms.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageRequest {

    @NotNull
    private Channel channel; // HTTP, RABBIT, WS

    // Common
    private String sender; // e.g., service/user name

    // HTTP
    private String httpUrl;           // target URL to send message to
    private Map<String, String> httpHeaders;
    private String httpBody;

    // RabbitMQ
    private String rabbitExchange;    // optional, default ""
    private String rabbitRoutingKey;  // optional, default "generic.queue"
    private String rabbitMessage;     // message body for Rabbit

    // WebSocket
    private String wsDestination;     // server-side broadcast topic, default "/topic/updates"
    private String wsMessage;         // message body for websocket

    public enum Channel { HTTP, RABBIT, WS }
}
