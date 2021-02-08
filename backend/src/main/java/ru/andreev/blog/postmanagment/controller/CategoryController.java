package ru.andreev.blog.postmanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.andreev.blog.domain.dto.request.CategoryRequest;
import ru.andreev.blog.postmanagment.service.CategoryService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(produces = "application/json", path = "/api/v/0/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return categoryService.findAllResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return categoryService.findById(id);
    }

    @GetMapping("/titles/{title}")
    public ResponseEntity<?> getByTitle(@PathVariable String title){ return categoryService.findByTitle(title);}

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> createNewCategory(@Valid @RequestBody final CategoryRequest categoryRequest){
        System.out.println(categoryRequest.getTitle());
        return categoryService.saveCategory(categoryRequest);
    }
}
