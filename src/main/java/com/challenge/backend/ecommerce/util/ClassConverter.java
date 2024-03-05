package com.challenge.backend.ecommerce.util;

import com.challenge.backend.ecommerce.dto.CategoryResponseDto;
import com.challenge.backend.ecommerce.entity.Category;

public class ClassConverter {
    public static CategoryResponseDto categoryToDto(Category category) {
        return new CategoryResponseDto(category.getName(), category.getDescription());
    }
}