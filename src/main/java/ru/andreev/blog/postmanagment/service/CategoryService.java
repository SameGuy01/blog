package ru.andreev.blog.postmanagment.service;

import ru.andreev.blog.domain.dto.request.CategoryRequest;
import ru.andreev.blog.domain.dto.response.CategoryResponse;
import ru.andreev.blog.domain.model.entity.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAll();

    void save(CategoryRequest categoryRequest);
}
