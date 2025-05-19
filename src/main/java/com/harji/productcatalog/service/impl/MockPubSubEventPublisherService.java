package com.harji.productcatalog.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harji.productcatalog.domain.ProductEventType;
import com.harji.productcatalog.dto.ProductDTO;
import com.harji.productcatalog.dto.ProductEventDTO;
import com.harji.productcatalog.service.EventPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * GCP Pub/Sub event publisher implementation.
 */
@Service
@Profile("pubsub")
public class MockPubSubEventPublisherService implements EventPublisherService {

    private static final Logger log = LoggerFactory.getLogger(MockPubSubEventPublisherService.class);

    private final ObjectMapper objectMapper;

    @Value("${app.event.topic}")
    private String topicName;

    public MockPubSubEventPublisherService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishProductEvent(ProductEventType eventType, ProductDTO productDTO) {
        try {
            ProductEventDTO eventDTO = ProductEventDTO.builder()
                    .eventType(eventType)
                    .timestamp(LocalDateTime.now())
                    .product(productDTO)
                    .build();

            String payload = objectMapper.writeValueAsString(eventDTO);

            // Publish to Pub/Sub topic
            log.info("MOCK PUB/SUB: Publishing to topic {}", topicName);
            log.info("MOCK PUB/SUB: Event type: {}", eventType);
            log.info("MOCK PUB/SUB: Payload: {}", payload);


            simulatePublish(payload);

            log.info("MOCK PUB/SUB: Successfully published event for product ID: {}", productDTO.getProductId());
        } catch (Exception e) {
            log.error("Failed to publish product event: {}", e.getMessage(), e);
        }
    }

    private void simulatePublish(String payload) {
        try {

            Thread.sleep(100);


            log.info("MOCK PUB/SUB: Message ID: mock-msg-" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while simulating publish", e);
        }
    }
}
