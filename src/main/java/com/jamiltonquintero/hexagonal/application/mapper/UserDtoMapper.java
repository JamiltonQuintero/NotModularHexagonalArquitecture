package com.jamiltonquintero.hexagonal.application.mapper;

import com.jamiltonquintero.hexagonal.domain.model.User;
import com.jamiltonquintero.hexagonal.domain.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") 
public interface UserDtoMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "country", target = "country")
    UserDto toDto(User domain);

}
