package com.challenge.backend.ecommerce.service;

import com.challenge.backend.ecommerce.dto.CategoryResponseDto;
import com.challenge.backend.ecommerce.entity.Category;
import com.challenge.backend.ecommerce.exceptions.GlobalException;
import com.challenge.backend.ecommerce.repository.CategoryRepository;
import com.challenge.backend.ecommerce.util.ClassConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplements implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImplements(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto findByID(Long id) {
        Optional<Category> foundCategory = categoryRepository.findById(id);
        return ClassConverter.categoryToDto(foundCategory.orElseThrow(() ->
                new GlobalException("Category with given id is not exist: " + id,
                        HttpStatus.NOT_FOUND)));
    }

    @Override
    public CategoryResponseDto save(Category category) {
        categoryRepository.save(category);
        return ClassConverter.categoryToDto(category);
    }

    @Override
    public CategoryResponseDto remove(Long id) {
        CategoryResponseDto foundCategory = findByID(id);
        categoryRepository.deleteById(id);
        return foundCategory;
    }

    @Override
    public List<String> findAll() {
        return new ArrayList<String>(categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList());
    }
}
