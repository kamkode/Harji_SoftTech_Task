package com.harji.productcatalog.service;

import com.harji.productcatalog.domain.ProductEventType;
import com.harji.productcatalog.dto.ProductDTO;

public interface EventPublisherService {

    void publishProductEvent(ProductEventType eventType, ProductDTO productDTO);
}
