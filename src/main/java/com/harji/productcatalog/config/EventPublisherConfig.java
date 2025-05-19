package com.harji.productcatalog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.harji.productcatalog.service.EventPublisherService;
import com.harji.productcatalog.service.impl.MockPubSubEventPublisherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class EventPublisherConfig {

    @Value("${app.event.publisher:default}")
    private String eventPublisher;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    // No need to define this bean as DefaultEventPublisherService is already annotated with @Primary

    @Bean
    @Primary
    @Profile("pubsub")
    public EventPublisherService pubSubEventPublisherService(MockPubSubEventPublisherService service) {
        return service;
    }


}
