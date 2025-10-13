package com.example.commservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "message_logs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class MessageLog {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private MessageChannel channel;

    @Enumerated(EnumType.STRING)
    private MessageDirection direction;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    /** hedef URL, exchange/routingKey veya ws destination */
    @Column(length = 512)
    private String target;

    @Lob
    private String payload;

    @Column(length = 256)
    private String responseSummary; // HTTP status, Rabbit msgId vs.

    @CreationTimestamp
    private OffsetDateTime createdAt;
}
