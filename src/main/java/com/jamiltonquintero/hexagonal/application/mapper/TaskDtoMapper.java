package com.jamiltonquintero.hexagonal.application.mapper;

import com.jamiltonquintero.hexagonal.domain.model.Task;
import com.jamiltonquintero.hexagonal.domain.model.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") 
public interface TaskDtoMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "timeRequiredToComplete", target = "timeRequiredToComplete")
    TaskDto toDto(Task domain);

}
