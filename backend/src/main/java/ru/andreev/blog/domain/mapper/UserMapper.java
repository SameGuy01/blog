package ru.andreev.blog.domain.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.andreev.blog.domain.dto.request.UserEditRequest;
import ru.andreev.blog.domain.model.entity.User;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toDto(UserEditRequest dto){

        return modelMapper.map(dto, User.class);
    }
}
