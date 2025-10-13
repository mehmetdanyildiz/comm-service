package com.example.commservice.dto;

import jakarta.validation.constraints.NotBlank;

public record SendRabbitRequest(
        String exchange,          // boş gelirse default kullanılır
        String routingKey,        // boş gelirse default kullanılır
        @NotBlank String payload
) {}
