package com.example.commservice.service;

import com.example.commservice.dto.SendHttpRequest;
import com.example.commservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HttpRelayService {
    private final LogService logService;
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> relay(SendHttpRequest req) {
        try {
            HttpMethod httpMethod = HttpMethod.valueOf(req.method().toUpperCase());
            HttpHeaders headers = new HttpHeaders();
            if (req.headers() != null) headers.setAll(req.headers());
            HttpEntity<String> entity = new HttpEntity<>(req.body(), headers);
            ResponseEntity<String> response = restTemplate.exchange(req.url(), httpMethod, entity, String.class);

            logService.log(MessageChannel.HTTP, MessageDirection.OUTBOUND, MessageStatus.SUCCESS,
                    req.url(), req.body(), "HTTP " + response.getStatusCode().value());
            return response;
        } catch (RestClientException | IllegalArgumentException ex) {
            logService.log(MessageChannel.HTTP, MessageDirection.OUTBOUND, MessageStatus.FAILED,
                    req.url(), req.body(), ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("HTTP relay failed: " + ex.getMessage());
        }
    }
}
