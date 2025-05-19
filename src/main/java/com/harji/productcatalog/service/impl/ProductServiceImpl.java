package com.harji.productcatalog.service.impl;

import com.harji.productcatalog.domain.Product;
import com.harji.productcatalog.domain.ProductEventType;
import com.harji.productcatalog.dto.ProductDTO;
import com.harji.productcatalog.exception.ProductNotFoundException;
import com.harji.productcatalog.repository.ProductRepository;
import com.harji.productcatalog.service.EventPublisherService;
import com.harji.productcatalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final EventPublisherService eventPublisherService;

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.info("Creating new product: {}", productDTO.getName());

        Product product = mapToEntity(productDTO);
        Product savedProduct = productRepository.save(product);

        ProductDTO savedProductDTO = mapToDTO(savedProduct);
        eventPublisherService.publishProductEvent(ProductEventType.PRODUCT_CREATED, savedProductDTO);

        return savedProductDTO;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(UUID productId, ProductDTO productDTO) {
        log.info("Updating product with ID: {}", productId);

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        updateProductFields(existingProduct, productDTO);
        Product updatedProduct = productRepository.save(existingProduct);

        ProductDTO updatedProductDTO = mapToDTO(updatedProduct);
        eventPublisherService.publishProductEvent(ProductEventType.PRODUCT_UPDATED, updatedProductDTO);

        return updatedProductDTO;
    }

    @Override
    public ProductDTO getProductById(UUID productId) {
        log.info("Fetching product with ID: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        return mapToDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products");

        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Product mapToEntity(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .category(productDTO.getCategory())
                .price(productDTO.getPrice())
                .availableStock(productDTO.getAvailableStock())
                .build();
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .availableStock(product.getAvailableStock())
                .lastUpdated(product.getLastUpdated())
                .build();
    }

    private void updateProductFields(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setAvailableStock(productDTO.getAvailableStock());
    }
}
