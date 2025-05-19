package com.harji.productcatalog.service;

import com.harji.productcatalog.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(UUID productId, ProductDTO productDTO);

    ProductDTO getProductById(UUID productId);

    List<ProductDTO> getAllProducts();
}
