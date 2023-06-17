package com.jamiltonquintero.hexagonal.infraestructure.adapter.mapper;

import com.jamiltonquintero.hexagonal.domain.model.User;
import com.jamiltonquintero.hexagonal.infraestructure.adapter.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDboMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "country", target = "country")
    UserEntity toDbo(User domain);

    @InheritInverseConfiguration
    User toDomain(UserEntity entity);
}