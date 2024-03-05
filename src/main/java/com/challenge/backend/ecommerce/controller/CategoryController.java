package com.challenge.backend.ecommerce.controller;

import com.challenge.backend.ecommerce.dto.CategoryResponseDto;
import com.challenge.backend.ecommerce.entity.Category;
import com.challenge.backend.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public CategoryResponseDto add(@RequestBody Category category) {
        return categoryService.save(category);
    }
    @GetMapping("/")
    public List<String> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseDto findById(@PathVariable Long id){
        return categoryService.findByID(id);
    }

    @DeleteMapping("/{id}")
    public CategoryResponseDto delete(@PathVariable Long id){
        return categoryService.remove(id);
    }
}
