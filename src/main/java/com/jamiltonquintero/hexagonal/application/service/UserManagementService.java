package com.jamiltonquintero.hexagonal.application.service;

import com.jamiltonquintero.hexagonal.application.usecases.UserService;
import com.jamiltonquintero.hexagonal.application.mapper.UserDtoMapper;
import com.jamiltonquintero.hexagonal.application.mapper.UserRequestMapper;
import com.jamiltonquintero.hexagonal.domain.model.Task;
import com.jamiltonquintero.hexagonal.domain.model.dto.UserDto;
import com.jamiltonquintero.hexagonal.domain.model.dto.request.UserRequest;
import com.jamiltonquintero.hexagonal.domain.port.TaskPersistencePort;
import com.jamiltonquintero.hexagonal.domain.port.UserPersistencePort;
import com.jamiltonquintero.hexagonal.infraestructure.adapter.exception.UserException;
import com.jamiltonquintero.hexagonal.domain.model.constant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManagementService implements UserService {

    private final UserPersistencePort userPersistencePort;
    private final TaskPersistencePort taskPersistencePort;
    private final UserRequestMapper userRequestMapper;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserManagementService(final UserPersistencePort userPersistencePort,
                                 final TaskPersistencePort taskPersistencePort,
                                 final UserRequestMapper userRequestMapper,
                                 final UserDtoMapper userDtoMapper) {
        this.userPersistencePort = userPersistencePort;
        this.taskPersistencePort = taskPersistencePort;
        this.userRequestMapper = userRequestMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDto createNew(UserRequest request) {
        var userRequest = userRequestMapper.toDomain(request);
        var userCreated = userPersistencePort.create(userRequest);
        return userDtoMapper.toDto(userCreated);
    }

    @Override
    public UserDto getById(Long id) {
        var user = userPersistencePort.getById(id);
        return userDtoMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        var users = userPersistencePort.getAll();
        return users
                .stream()
                .map(userDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        var user = userPersistencePort.getById(id);

        if(!user.getTasks().isEmpty()){
            throw new UserException(HttpStatus.BAD_REQUEST,
                    String.format(UserConstant.CURRENT_USER_NOT_ALLOW_TO_DELETE, user.getName()));
        }

        userPersistencePort.deleteById(id);
    }

    @Override
    public UserDto update(UserRequest request, Long id) {
        var userToUpdate = userRequestMapper.toDomain(request);

        userToUpdate.setName(request.getName());
        userToUpdate.setAge(request.getAge());
        userToUpdate.setCountry(request.getCountry());

        var userUpdated = userPersistencePort.update(userToUpdate);

        return userDtoMapper.toDto(userUpdated);
    }

    @Override
    public void assignTasks(Long id, List<Long> tasksIds) {

        var user = userPersistencePort.getById(id);

        var tasksToDo = taskPersistencePort.getByIds(tasksIds);

        validateAvailabilityToAssign(new ArrayList<>(user.getTasks()), tasksToDo);

        user.getTasks().addAll(tasksToDo);

        userPersistencePort.update(user);
    }

    private void validateAvailabilityToAssign(List<Task> currentTasks, List<Task> tasksToDo) {

        int totalTimeInTasks = getRequirementTimeToDo(currentTasks,tasksToDo);

        var isNotAllow = totalTimeInTasks > 8;

        if (isNotAllow){
            throw new UserException(HttpStatus.BAD_REQUEST,
                    String.format(UserConstant.CURRENT_USER_NOT_ALLOW_TO_DO_TASKS, totalTimeInTasks));
        }

    }

    private int getRequirementTimeToDo(List<Task> currentTasks, List<Task> tasksToDo) {

        var timeInCurrentTasks = currentTasks.stream()
                .mapToInt(Task::getTimeRequiredToComplete)
                .sum();

        var timeInTasksToDo = tasksToDo.stream()
                .mapToInt(Task::getTimeRequiredToComplete)
                .sum();

        return timeInCurrentTasks + timeInTasksToDo;
    }

}
