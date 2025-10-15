package com.example.genericcoms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String DEFAULT_QUEUE = "generic.queue";

    @Bean
    public Queue defaultQueue() {
        return new Queue(DEFAULT_QUEUE, false);
    }
}
