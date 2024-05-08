package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository repo;
    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        try {
            Category categorySave = new Category(category.getName());
            return repo.save(categorySave);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate = repo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(categoryUpdate.is_activated());
            categoryUpdate.set_deleted(categoryUpdate.is_deleted());
        }catch (Exception e){
            e.printStackTrace();
        }
        return repo.save((categoryUpdate));
    }

    @Override
    public void deleteById(Long id) {
        Category category = repo.getById(id);
        category.set_activated(false);
        category.set_deleted(true);
        repo.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = repo.getById(id);
        category.set_activated(true);
        category.set_deleted(false);
        repo.save(category);
    }
}
