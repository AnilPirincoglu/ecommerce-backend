package dev.anilp.ecommerce_backend.mapper;

import dev.anilp.ecommerce_backend.dto.product.CreateProductRequestDTO;
import dev.anilp.ecommerce_backend.dto.product.ProductResponseDTO;
import dev.anilp.ecommerce_backend.dto.product.UpdateProductRequestDTO;
import dev.anilp.ecommerce_backend.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO productToResponseDTO(Product product);

    List<ProductResponseDTO> productsToResponseDTOList(List<Product> products);

    Product requestDTOToProduct(CreateProductRequestDTO createProduct);

    void updateProductFromDTO(@MappingTarget Product product, UpdateProductRequestDTO updateProduct);
}
