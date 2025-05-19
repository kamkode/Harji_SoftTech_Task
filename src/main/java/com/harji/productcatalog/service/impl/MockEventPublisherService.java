package com.harji.productcatalog.service.impl;

import com.harji.productcatalog.domain.ProductEventType;
import com.harji.productcatalog.dto.ProductDTO;
import com.harji.productcatalog.service.EventPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class MockEventPublisherService implements EventPublisherService {

    private static final Logger log = LoggerFactory.getLogger(MockEventPublisherService.class);

    @Override
    public void publishProductEvent(ProductEventType eventType, ProductDTO productDTO) {
        log.info("MOCK: Published {} event for product ID: {}", eventType, productDTO.getProductId());
    }
}
