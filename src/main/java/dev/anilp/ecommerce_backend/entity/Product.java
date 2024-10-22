package dev.anilp.ecommerce_backend.entity;

import dev.anilp.ecommerce_backend.entity.base_entity.BaseEntityForMongo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product extends BaseEntityForMongo {
    @Id
    private String id;

    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 3, max = 50, message = "Name field must be between 3 and 50 characters")
    @Field(name = "name")
    private String name;

    @NotBlank(message = "Short description field cannot be blank")
    @Size(min = 3, max = 100, message = "Short description field must be between 3 and 100 characters")
    @Field(name = "short_description")
    private String shortDescription;

    @NotBlank(message = "Description field cannot be blank")
    @Size(min = 3, max = 1000, message = "Description field must be between 3 and 1000 characters")
    @Field(name = "description")
    private String description;

    @NotBlank(message = "Image URL field cannot be blank")
    @Size(min = 3, max = 255, message = "Image URL field must be between 3 and 255 characters")
    @Field(name = "image_url")
    private String imageUrl;

    @NotNull(message = "Price field cannot be null")
    @DecimalMin(value = "1", message = "Price field must be greater than 0")
    @Field(name = "price")
    private BigDecimal price = BigDecimal.ZERO;

    @NotNull(message = "Stock quantity field cannot be null")
    @Min(value = 1, message = "Stock quantity field must be greater than 0")
    @Field(name = "stock_quantity")
    private Integer stockQuantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product product)) {
            return false;
        }
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(shortDescription, product.shortDescription) && Objects.equals(description, product.description) && Objects.equals(imageUrl, product.imageUrl) && Objects.equals(price, product.price) && Objects.equals(stockQuantity, product.stockQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortDescription, description, imageUrl, price, stockQuantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", stock=" + stockQuantity +
                '}';
    }
}
