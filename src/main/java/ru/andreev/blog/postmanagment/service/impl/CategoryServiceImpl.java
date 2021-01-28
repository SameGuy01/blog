package ru.andreev.blog.postmanagment.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.blog.domain.dto.request.CategoryRequest;
import ru.andreev.blog.domain.dto.response.CategoryResponse;
import ru.andreev.blog.domain.mapper.CategoryMapper;
import ru.andreev.blog.domain.model.entity.Category;
import ru.andreev.blog.postmanagment.repository.CategoryRepository;
import ru.andreev.blog.postmanagment.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(CategoryRequest categoryRequest) {
        categoryRepository.save(categoryMapper.toEntity(categoryRequest));
    }
}
