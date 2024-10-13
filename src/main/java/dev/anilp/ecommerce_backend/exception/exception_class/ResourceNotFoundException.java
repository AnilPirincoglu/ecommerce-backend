package dev.anilp.ecommerce_backend.exception.exception_class;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with given %s not found: %s", resourceName, fieldName, fieldValue));
    }
}
