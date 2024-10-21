package dev.anilp.ecommerce_backend.exception.exception_class;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with given %s: %s", resourceName, fieldName, fieldValue));
    }
}
