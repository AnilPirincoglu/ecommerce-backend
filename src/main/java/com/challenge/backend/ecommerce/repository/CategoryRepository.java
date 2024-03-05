package com.challenge.backend.ecommerce.repository;

import com.challenge.backend.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
