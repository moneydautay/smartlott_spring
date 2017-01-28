package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Category;
import com.smartlott.backend.persistence.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by greenlucky on 1/27/17.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new category
     * @param category
     * @return A category after created
     */
    public Category create(Category category){
        return categoryRepository.save(category);
    }

    /**
     * Updates a category
     * @param category
     * @return a category after updated
     */
    public Category update(Category category){
        return categoryRepository.save(category);
    }

    public void delete(int categoryId){
        categoryRepository.delete(categoryId);
    }

    public Category getOne(int categoryId){
        return categoryRepository.findOne(categoryId);
    }

    /**
     * Gets all category or null if not exist
     * @return a list of category
     */
    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    /**
     * Gets all category or null if not exist
     * @param pageable
     * @return A page of category or null if not exist
     */
    public Page<Category> getAll(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }

    public boolean exist(int categoryId) {
        return categoryRepository.exists(categoryId);
    }
}
