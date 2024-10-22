package dev.anilp.ecommerce_backend.controller;

import dev.anilp.ecommerce_backend.dto.product.CreateProductRequestDTO;
import dev.anilp.ecommerce_backend.dto.product.ProductResponseDTO;
import dev.anilp.ecommerce_backend.dto.product.UpdateProductRequestDTO;
import dev.anilp.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{productId}")
    public ProductResponseDTO getProductById(@PathVariable String productId) {
        return productService.getProductById(productId);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public void saveProduct(@Valid @RequestBody CreateProductRequestDTO createRequest) {
        productService.saveProduct(createRequest);
    }

    @PutMapping("{productId}")
    public void updateProduct(@PathVariable String productId,
                              @Valid @RequestBody UpdateProductRequestDTO updateRequest) {
        productService.updateProduct(productId, updateRequest);
    }

    @DeleteMapping("{productId}")
    public void deleteProduct(@PathVariable String productId) {
        productService.deleteProductById(productId);
    }
}
