package com.jamiltonquintero.hexagonal.application.service;

import com.jamiltonquintero.hexagonal.application.usecases.TaskService;
import com.jamiltonquintero.hexagonal.application.mapper.TaskDtoMapper;
import com.jamiltonquintero.hexagonal.application.mapper.TaskRequestMapper;
import com.jamiltonquintero.hexagonal.domain.model.dto.TaskDto;
import com.jamiltonquintero.hexagonal.domain.model.dto.request.TaskRequest;
import com.jamiltonquintero.hexagonal.domain.port.TaskAudioPort;
import com.jamiltonquintero.hexagonal.domain.port.TaskPersistencePort;
import com.jamiltonquintero.hexagonal.infraestructure.adapter.exception.UserException;
import com.jamiltonquintero.hexagonal.domain.model.constant.TaskConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskManagementService implements TaskService {

    private final TaskPersistencePort taskPersistencePort;
    private final TaskRequestMapper taskRequestMapper;
    private final TaskDtoMapper taskDtoMapper;

    private final TaskAudioPort taskAudioPort;
    @Autowired
    public TaskManagementService(final TaskPersistencePort taskPersistencePort,
                                 final TaskRequestMapper taskRequestMapper,
                                 final TaskDtoMapper taskDtoMapper, TaskAudioPort taskAudioPort) {
        this.taskPersistencePort = taskPersistencePort;
        this.taskRequestMapper = taskRequestMapper;
        this.taskDtoMapper = taskDtoMapper;
        this.taskAudioPort = taskAudioPort;
    }

    @Override
    public TaskDto createNew(TaskRequest request) {
        var taskToCreate = taskRequestMapper.toDomain(request);

        var taskCreated = taskPersistencePort.create(taskToCreate);

        var urlAudio = taskAudioPort.urlAudio();

        return taskDtoMapper.toDto(taskCreated);
    }

    @Override
    public TaskDto getById(Long id) {
        var task = taskPersistencePort.getById(id);

        return taskDtoMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getAll() {
        var tasks = taskPersistencePort.getAll();
        return tasks
                .stream()
                .map(taskDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        var task = taskPersistencePort.getById(id);

        if(!task.getUsers().isEmpty()){
            throw new UserException(HttpStatus.BAD_REQUEST,
                    String.format(TaskConstant.CURRENT_TASK_NOT_ALLOW_TO_DELETE, task.getId()));
        }
        taskPersistencePort.deleteById(id);
    }

    @Override
    public TaskDto update(TaskRequest request, Long id) {
        var taskToUpdate = taskRequestMapper.toDomain(request);

        taskToUpdate.setName(request.getName());
        taskToUpdate.setDescription(request.getDescription());
        taskToUpdate.setTimeRequiredToComplete(request.getTimeRequiredToComplete());

        var taskUpdated = taskPersistencePort.update(taskToUpdate);

        return taskDtoMapper.toDto(taskUpdated);

    }

}
