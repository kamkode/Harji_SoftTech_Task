package com.harji.productcatalog.dto;

import com.harji.productcatalog.domain.ProductEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEventDTO {

    private ProductEventType eventType;
    private LocalDateTime timestamp;
    private ProductDTO product;
}
