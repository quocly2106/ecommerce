package com.ecommerce.library.service;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    /*Admin*/
    List<Category> findAll();
    Category save(Category catelogy);
    Category findById(Long id);
    Category update(Category catelogy);
    void deleteById(Long id);
    void enableById(Long id);
    List<Category> findAllByActivated();

    /*Customer*/
    List<CategoryDto> getCategoryAndProduct();

}
