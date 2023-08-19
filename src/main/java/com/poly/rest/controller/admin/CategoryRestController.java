package com.poly.rest.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Category;
import com.poly.service.CategoryService;

@CrossOrigin("*")
@RestController
public class CategoryRestController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories
    @GetMapping("/rest/category")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    // Get one category by id
    @GetMapping("/rest/category/{id}")
    public ResponseEntity<Category> findOne(@PathVariable("id") String id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    // Create a new category
    @PostMapping("/rest/category")
    public ResponseEntity<Category> post(@RequestBody Category category) {
        Category createdCategory = categoryService.create(category);
        return ResponseEntity.ok(createdCategory);
    }

    // Update a category
    @PutMapping("/rest/category/{id}")
    public ResponseEntity<Category> put(@PathVariable("id") String id, @RequestBody Category category) {
        Category existingCategory = categoryService.findById(id);
        if (existingCategory != null) {
            category.setId(id);
            Category updatedCategory = categoryService.update(category);
            return ResponseEntity.ok(updatedCategory);
        }
        return ResponseEntity.notFound().build(); // 404 not found
    }

    // Delete category
    @DeleteMapping("/rest/category/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Category existingCategory = categoryService.findById(id);
        if (existingCategory != null) {
            categoryService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build(); // 404 not found
    }
}
