package ru.andreev.blog.postmanagment.service;

import org.springframework.http.ResponseEntity;
import ru.andreev.blog.domain.dto.request.CategoryRequest;
import ru.andreev.blog.domain.dto.response.CategoryResponse;
import ru.andreev.blog.domain.model.entity.Category;

import java.util.List;

public interface CategoryService {

    ResponseEntity<List<CategoryResponse>> findAllResponse();

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> save(CategoryRequest categoryRequest);
}
