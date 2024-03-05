package com.challenge.backend.ecommerce.service;

import com.challenge.backend.ecommerce.dto.CategoryResponseDto;
import com.challenge.backend.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto findByID(Long id);
    CategoryResponseDto save(Category category);
    CategoryResponseDto remove(Long id);
    List<String> findAll();
}
