package dev.anilp.ecommerce_backend.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequestDTO(
        @NotBlank(message = "Name field cannot be blank")
        @Size(min = 3, max = 50, message = "Name field must be between 3 and 50 characters")
        String name,

        @NotBlank(message = "Short description field cannot be blank")
        @Size(min = 3, max = 100, message = "Short description field must be between 3 and 100 characters")
        String shortDescription,

        @NotBlank(message = "Description field cannot be blank")
        @Size(min = 3, max = 1000, message = "Description field must be between 3 and 1000 characters")
        String description,

        @NotBlank(message = "Image URL field cannot be blank")
        @Size(min = 3, max = 255, message = "Image URL field must be between 3 and 255 characters")
        String imageUrl,

        @NotNull(message = "Price field cannot be null")
        @DecimalMin(value = "1", message = "Price field must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Stock quantity field cannot be null")
        @Min(value = 1, message = "Stock quantity field must be greater than 0")
        Integer stockQuantity
) {
}
