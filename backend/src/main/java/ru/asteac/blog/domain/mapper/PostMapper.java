package ru.asteac.blog.domain.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.asteac.blog.domain.dto.request.PostEditRequest;
import ru.asteac.blog.domain.dto.request.PostRequest;
import ru.asteac.blog.domain.dto.response.PostResponse;
import ru.asteac.blog.domain.model.entity.Post;

@Component
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Post toEntity(PostRequest dto){
        return modelMapper.map(dto, Post.class);
    }

    public PostResponse toDto(Post entity){
        return modelMapper.map(entity, PostResponse.class);
    }

    public Post toEntity(PostEditRequest dto) {
        return modelMapper.map(dto, Post.class);
    }
}
