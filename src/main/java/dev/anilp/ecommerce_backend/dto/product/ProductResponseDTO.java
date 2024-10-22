package dev.anilp.ecommerce_backend.dto.product;

import java.math.BigDecimal;

public record ProductResponseDTO(String id, String name, String shortDescription, String description, String imageUrl,
                                 BigDecimal price, Integer stockQuantity) {
}
