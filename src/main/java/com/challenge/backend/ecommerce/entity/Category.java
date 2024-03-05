package com.challenge.backend.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "category",schema = "ecommerce")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Category name can not be blank")
    @Size(min = 3, max = 30, message = "Category name must be more than 3 and less than 30 character")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Category description can not be blank")
    @Size(min = 3, max = 100, message = "Description must be more than 3 and less than 100 character")
    private String description;
}
