package ru.andreev.blog.domain.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.andreev.blog.domain.dto.request.PostRequest;
import ru.andreev.blog.domain.dto.response.PostResponse;
import ru.andreev.blog.domain.model.entity.Post;

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
}
