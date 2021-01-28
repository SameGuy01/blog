package ru.andreev.blog.domain.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.andreev.blog.domain.dto.request.CategoryRequest;
import ru.andreev.blog.domain.dto.response.CategoryResponse;
import ru.andreev.blog.domain.model.entity.Category;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Category toEntity(CategoryRequest dto){
        return modelMapper.map(dto, Category.class);
    }

    public CategoryResponse toDto(Category category){
        return modelMapper.map(category, CategoryResponse.class);
    }
}
