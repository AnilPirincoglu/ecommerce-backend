package dev.anilp.ecommerce_backend.service;

import dev.anilp.ecommerce_backend.dto.product.CreateProductRequestDTO;
import dev.anilp.ecommerce_backend.dto.product.ProductResponseDTO;
import dev.anilp.ecommerce_backend.dto.product.UpdateProductRequestDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO getProductById(String id);

    List<ProductResponseDTO> getAllProducts();

    void saveProduct(CreateProductRequestDTO createProduct);

    void updateProduct(String id, UpdateProductRequestDTO updateProduct);

    void deleteProductById(String id);
}
