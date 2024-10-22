package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
