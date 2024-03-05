package com.challenge.backend.ecommerce.util;

import com.challenge.backend.ecommerce.dto.CategoryResponseDto;
import com.challenge.backend.ecommerce.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class ClassConverter {
    public static CategoryResponseDto categoryToDto(Category category) {
        return new CategoryResponseDto(category.getName(), category.getDescription());
    }

    public static List<CategoryResponseDto> listOfcategoryToDto(List<Category> categories) {
        return new ArrayList<>(categories.stream()
                .map(ClassConverter::categoryToDto)
                .toList());
    }
}