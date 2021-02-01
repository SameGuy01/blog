package ru.andreev.blog.domain.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.andreev.blog.domain.dto.request.CommentRequest;
import ru.andreev.blog.domain.dto.response.CommentResponse;
import ru.andreev.blog.domain.model.entity.Comment;

@Component
public class CommentMapper {
    private final ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Comment toEntity(CommentRequest dto){
        return modelMapper.map(dto, Comment.class);
    }

    public CommentResponse toDto(Comment entity){
        return modelMapper.map(entity, CommentResponse.class);
    }
}
