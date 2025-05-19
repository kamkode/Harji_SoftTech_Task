package com.harji.productcatalog.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harji.productcatalog.domain.Product;
import com.harji.productcatalog.dto.ProductDTO;
import com.harji.productcatalog.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .name("Integration Test Product")
                .description("Product for integration testing")
                .category("Test")
                .price(new BigDecimal("99.99"))
                .availableStock(10)
                .build();
        
        testProduct = productRepository.save(testProduct);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        // Arrange
        ProductDTO inputDTO = ProductDTO.builder()
                .name("New Test Product")
                .description("New product for testing")
                .category("Test")
                .price(new BigDecimal("149.99"))
                .availableStock(20)
                .build();

        // Act & Assert
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Test Product")))
                .andExpect(jsonPath("$.description", is("New product for testing")))
                .andExpect(jsonPath("$.category", is("Test")))
                .andExpect(jsonPath("$.price", is(149.99)))
                .andExpect(jsonPath("$.availableStock", is(20)));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        ProductDTO updateDTO = ProductDTO.builder()
                .name("Updated Product")
                .description("Updated description")
                .category("Updated Category")
                .price(new BigDecimal("199.99"))
                .availableStock(50)
                .build();

        // Act & Assert
        mockMvc.perform(put("/products/{id}", testProduct.getProductId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(testProduct.getProductId().toString())))
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.description", is("Updated description")))
                .andExpect(jsonPath("$.category", is("Updated Category")))
                .andExpect(jsonPath("$.price", is(199.99)))
                .andExpect(jsonPath("$.availableStock", is(50)));
    }

    @Test
    void getProductById_ShouldReturnProduct() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/products/{id}", testProduct.getProductId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(testProduct.getProductId().toString())))
                .andExpect(jsonPath("$.name", is("Integration Test Product")))
                .andExpect(jsonPath("$.description", is("Product for integration testing")))
                .andExpect(jsonPath("$.category", is("Test")))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.availableStock", is(10)));
    }

    @Test
    void getProductById_WhenProductNotFound_ShouldReturnNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/products/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productId", is(testProduct.getProductId().toString())))
                .andExpect(jsonPath("$[0].name", is("Integration Test Product")))
                .andExpect(jsonPath("$[0].description", is("Product for integration testing")))
                .andExpect(jsonPath("$[0].category", is("Test")))
                .andExpect(jsonPath("$[0].price", is(99.99)))
                .andExpect(jsonPath("$[0].availableStock", is(10)));
    }
}
