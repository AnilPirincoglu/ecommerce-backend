package dev.anilp.ecommerce_backend.exception.exception_class;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with given %s is already exists: %s", resourceName, fieldName, fieldValue));
    }
}
