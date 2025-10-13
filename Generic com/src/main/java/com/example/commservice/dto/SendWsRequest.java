package com.example.commservice.dto;

import jakarta.validation.constraints.NotBlank;

public record SendWsRequest(
        @NotBlank String destination, // örn: /topic/broadcast
        @NotBlank String payload
) {}
