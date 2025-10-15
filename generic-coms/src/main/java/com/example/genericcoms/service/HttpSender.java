package com.example.genericcoms.service;

import com.example.genericcoms.model.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HttpSender {
    private static final Logger log = LoggerFactory.getLogger(HttpSender.class);
    private final StatsService stats;
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> send(MessageRequest req) {
        try {
            if (req.getHttpUrl() == null || req.getHttpUrl().isBlank()) {
                stats.incHttpFailure();
                return ResponseEntity.badRequest().body("httpUrl is required for HTTP channel");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (req.getSender() != null) headers.add("X-Sender", req.getSender());
            if (req.getHttpHeaders() != null) {
                for (Map.Entry<String, String> e : req.getHttpHeaders().entrySet()) {
                    headers.add(e.getKey(), e.getValue());
                }
            }
            HttpEntity<String> entity = new HttpEntity<>(req.getHttpBody() == null ? "" : req.getHttpBody(), headers);
            ResponseEntity<String> resp = restTemplate.exchange(req.getHttpUrl(), HttpMethod.POST, entity, String.class);
            if (resp.getStatusCode().is2xxSuccessful()) {
                stats.incHttpSuccess();
            } else {
                stats.incHttpFailure();
            }
            return resp;
        } catch (RestClientException ex) {
            log.error("HTTP send failed", ex);
            stats.incHttpFailure();
            return ResponseEntity.status(502).body("HTTP send failed: " + ex.getMessage());
        }
    }
}
