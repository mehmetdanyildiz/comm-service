package com.example.commservice.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

public record SendHttpRequest(
        @NotBlank String method, // GET, POST, PUT, DELETE...
        @NotBlank String url,
        Map<String, String> headers,
        String body
) {}
