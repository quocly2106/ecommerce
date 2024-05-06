package com.ecommerce.library.service;

import com.ecommerce.library.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    List<Category> findAll();
    Category save(Category catelogy);
    Category findById(Long id);
    Category update(Category catelogy);
    void deleteById(Long id);
    void enabledById(Long id);
}
