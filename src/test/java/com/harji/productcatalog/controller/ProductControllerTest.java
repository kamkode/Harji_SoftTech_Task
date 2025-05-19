package com.harji.productcatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harji.productcatalog.dto.ProductDTO;
import com.harji.productcatalog.exception.ProductNotFoundException;
import com.harji.productcatalog.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private UUID productId;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        
        productDTO = ProductDTO.builder()
                .productId(productId)
                .name("Test Product")
                .description("Test Description")
                .category("Test Category")
                .price(new BigDecimal("99.99"))
                .availableStock(10)
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        // Arrange
        ProductDTO inputDTO = ProductDTO.builder()
                .name("Test Product")
                .description("Test Description")
                .category("Test Category")
                .price(new BigDecimal("99.99"))
                .availableStock(10)
                .build();
        
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);
        
        // Act & Assert
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId", is(productId.toString())))
                .andExpect(jsonPath("$.name", is("Test Product")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.category", is("Test Category")))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.availableStock", is(10)));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        ProductDTO inputDTO = ProductDTO.builder()
                .name("Updated Product")
                .description("Updated Description")
                .category("Updated Category")
                .price(new BigDecimal("199.99"))
                .availableStock(20)
                .build();
        
        ProductDTO updatedDTO = ProductDTO.builder()
                .productId(productId)
                .name("Updated Product")
                .description("Updated Description")
                .category("Updated Category")
                .price(new BigDecimal("199.99"))
                .availableStock(20)
                .lastUpdated(LocalDateTime.now())
                .build();
        
        when(productService.updateProduct(eq(productId), any(ProductDTO.class))).thenReturn(updatedDTO);
        
        // Act & Assert
        mockMvc.perform(put("/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(productId.toString())))
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.description", is("Updated Description")))
                .andExpect(jsonPath("$.category", is("Updated Category")))
                .andExpect(jsonPath("$.price", is(199.99)))
                .andExpect(jsonPath("$.availableStock", is(20)));
    }

    @Test
    void getProductById_ShouldReturnProduct() throws Exception {
        // Arrange
        when(productService.getProductById(productId)).thenReturn(productDTO);
        
        // Act & Assert
        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(productId.toString())))
                .andExpect(jsonPath("$.name", is("Test Product")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.category", is("Test Category")))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.availableStock", is(10)));
    }

    @Test
    void getProductById_WhenProductNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(productService.getProductById(productId)).thenThrow(new ProductNotFoundException("Product not found"));
        
        // Act & Assert
        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() throws Exception {
        // Arrange
        when(productService.getAllProducts()).thenReturn(List.of(productDTO));
        
        // Act & Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productId", is(productId.toString())))
                .andExpect(jsonPath("$[0].name", is("Test Product")))
                .andExpect(jsonPath("$[0].description", is("Test Description")))
                .andExpect(jsonPath("$[0].category", is("Test Category")))
                .andExpect(jsonPath("$[0].price", is(99.99)))
                .andExpect(jsonPath("$[0].availableStock", is(10)));
    }
}
