package com.harji.productcatalog.service;

import com.harji.productcatalog.domain.Product;
import com.harji.productcatalog.domain.ProductEventType;
import com.harji.productcatalog.dto.ProductDTO;
import com.harji.productcatalog.exception.ProductNotFoundException;
import com.harji.productcatalog.repository.ProductRepository;
import com.harji.productcatalog.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EventPublisherService eventPublisherService;

    @InjectMocks
    private ProductServiceImpl productService;

    private UUID productId;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        
        product = Product.builder()
                .productId(productId)
                .name("Test Product")
                .description("Test Description")
                .category("Test Category")
                .price(new BigDecimal("99.99"))
                .availableStock(10)
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
        
        productDTO = ProductDTO.builder()
                .name("Test Product")
                .description("Test Description")
                .category("Test Category")
                .price(new BigDecimal("99.99"))
                .availableStock(10)
                .build();
    }

    @Test
    void createProduct_ShouldReturnCreatedProductDTO() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        // Act
        ProductDTO result = productService.createProduct(productDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getDescription(), result.getDescription());
        assertEquals(productDTO.getCategory(), result.getCategory());
        assertEquals(productDTO.getPrice(), result.getPrice());
        assertEquals(productDTO.getAvailableStock(), result.getAvailableStock());
        
        verify(productRepository, times(1)).save(any(Product.class));
        verify(eventPublisherService, times(1)).publishProductEvent(eq(ProductEventType.PRODUCT_CREATED), any(ProductDTO.class));
    }

    @Test
    void updateProduct_WhenProductExists_ShouldReturnUpdatedProductDTO() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        // Act
        ProductDTO result = productService.updateProduct(productId, productDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(productDTO.getName(), result.getName());
        
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(eventPublisherService, times(1)).publishProductEvent(eq(ProductEventType.PRODUCT_UPDATED), any(ProductDTO.class));
    }

    @Test
    void updateProduct_WhenProductDoesNotExist_ShouldThrowProductNotFoundException() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, productDTO));
        
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
        verify(eventPublisherService, never()).publishProductEvent(any(ProductEventType.class), any(ProductDTO.class));
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProductDTO() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        
        // Act
        ProductDTO result = productService.getProductById(productId);
        
        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(product.getName(), result.getName());
        
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_WhenProductDoesNotExist_ShouldThrowProductNotFoundException() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
        
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getAllProducts_ShouldReturnListOfProductDTOs() {
        // Arrange
        when(productRepository.findAll()).thenReturn(List.of(product));
        
        // Act
        List<ProductDTO> result = productService.getAllProducts();
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productId, result.get(0).getProductId());
        
        verify(productRepository, times(1)).findAll();
    }
}
