package ru.asteac.blog.domain.mapper;

import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.asteac.blog.domain.dto.request.PostEditRequest;
import ru.asteac.blog.domain.dto.request.PostRequest;
import ru.asteac.blog.domain.dto.response.PostResponse;
import ru.asteac.blog.domain.dto.response.SimplePostResponse;
import ru.asteac.blog.domain.model.entity.Post;
import ru.asteac.blog.domain.model.entity.User;

import java.util.Set;

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

    Converter<Set<User>, Integer> listToSize = new AbstractConverter<>() {
        @Override
        protected Integer convert(Set<User> likeUsers) {
            if (likeUsers == null) {
                return 0;
            } else {
                if (likeUsers.size() > 0) {
                    return likeUsers.size();
                } else {
                    return 0;
                }
            }
        }
    };

    public SimplePostResponse toSimpleDto(Post entity){
        modelMapper.addConverter(listToSize);
        return modelMapper.map(entity, SimplePostResponse.class);
    }
}
