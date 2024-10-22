package dev.anilp.ecommerce_backend.service.impl;

import dev.anilp.ecommerce_backend.dto.product.CreateProductRequestDTO;
import dev.anilp.ecommerce_backend.dto.product.ProductResponseDTO;
import dev.anilp.ecommerce_backend.dto.product.UpdateProductRequestDTO;
import dev.anilp.ecommerce_backend.entity.Product;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.ProductMapper;
import dev.anilp.ecommerce_backend.repository.ProductRepository;
import dev.anilp.ecommerce_backend.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ID;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.PRODUCT;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        return mapper.productToResponseDTO(
                findProductById(id));
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return mapper.productsToResponseDTOList(
                productRepository.findAll()
        );
    }

    @Override
    public void saveProduct(CreateProductRequestDTO createRequest) {
        productRepository.save(
                mapper.requestDTOToProduct(createRequest));
    }

    @Override
    public void updateProduct(String id, UpdateProductRequestDTO updateRequest) {
        Product product = findProductById(id);
        mapper.updateProductFromDTO(product, updateRequest);
        productRepository.save(product);
    }

    @Override
    public void deleteProductById(String id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private Product findProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, id));
    }
}
