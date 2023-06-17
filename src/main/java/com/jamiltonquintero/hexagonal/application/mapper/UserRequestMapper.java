package com.jamiltonquintero.hexagonal.application.mapper;

import com.jamiltonquintero.hexagonal.domain.model.User;
import com.jamiltonquintero.hexagonal.domain.model.dto.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") 
public interface UserRequestMapper {


    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "country", target = "country")
    User toDomain(UserRequest request);
}
