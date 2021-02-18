package ru.andreev.blog.domain.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.andreev.blog.domain.dto.request.UserInfoEditRequest;
import ru.andreev.blog.domain.dto.response.UserInfoResponse;
import ru.andreev.blog.domain.model.entity.User;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(UserInfoEditRequest dto){
        return modelMapper.map(dto, User.class);
    }

    public UserInfoResponse toDto(User entity){
        return  modelMapper.map(entity, UserInfoResponse.class);
    }
}
