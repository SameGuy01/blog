package ru.andreev.blog.postmanagment.service.impl;

import com.sun.mail.iap.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.andreev.blog.domain.dto.request.CategoryRequest;
import ru.andreev.blog.domain.dto.response.CategoryResponse;
import ru.andreev.blog.domain.mapper.CategoryMapper;
import ru.andreev.blog.domain.model.entity.Category;
import ru.andreev.blog.postmanagment.exception.CategoryNotFountException;
import ru.andreev.blog.postmanagment.repository.CategoryRepository;
import ru.andreev.blog.postmanagment.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public ResponseEntity<List<CategoryResponse>> findAllResponse() {
        List<CategoryResponse> categoryListResponse = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryListResponse);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFountException(id));
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @Override
    public ResponseEntity<?> save(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
