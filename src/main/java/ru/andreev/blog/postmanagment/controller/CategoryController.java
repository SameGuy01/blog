package ru.andreev.blog.postmanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreev.blog.domain.dto.request.CategoryRequest;
import ru.andreev.blog.domain.dto.response.CategoryResponse;
import ru.andreev.blog.domain.dto.response.MessageResponse;
import ru.andreev.blog.postmanagment.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(produces = "application/json", path = "/api/v/0/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    private ResponseEntity<?> getAll(){
        List<CategoryResponse> categoryList = categoryService.findAll();
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        categoryService.save(categoryRequest);
        return ResponseEntity.ok(new MessageResponse("Category created successfully!"));
    }
}
