package com.harji.productcatalog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harji.productcatalog.domain.ProductEventType;
import com.harji.productcatalog.dto.ProductDTO;
import com.harji.productcatalog.dto.ProductEventDTO;
import com.harji.productcatalog.service.EventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Default implementation of EventPublisherService that logs events.
 */
@Service
@Primary
@RequiredArgsConstructor
public class DefaultEventPublisherService implements EventPublisherService {

    private static final Logger log = LoggerFactory.getLogger(DefaultEventPublisherService.class);

    private final ObjectMapper objectMapper;

    @org.springframework.beans.factory.annotation.Value("${app.event.topic}")
    private String eventTopic;

    @Override
    public void publishProductEvent(ProductEventType eventType, ProductDTO productDTO) {
        try {
            ProductEventDTO eventDTO = ProductEventDTO.builder()
                    .eventType(eventType)
                    .timestamp(LocalDateTime.now())
                    .product(productDTO)
                    .build();

            String payload = objectMapper.writeValueAsString(eventDTO);
            log.info("EVENT PUBLISHED to topic {}: {}", eventTopic, eventType);
            log.info("EVENT PAYLOAD: {}", payload);
            log.info("EVENT for product ID: {}", productDTO.getProductId());
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event: {}", e.getMessage(), e);
        }
    }
}
