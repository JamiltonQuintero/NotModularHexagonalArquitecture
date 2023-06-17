package com.jamiltonquintero.hexagonal.infraestructure.rest.controller;

import com.jamiltonquintero.hexagonal.application.usecases.TaskService;
import com.jamiltonquintero.hexagonal.domain.model.dto.TaskDto;
import com.jamiltonquintero.hexagonal.domain.model.dto.request.TaskRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id){
        return taskService.getById(id);
    }

    @GetMapping
    public List<TaskDto> getAll() {
        return taskService.getAll();
    }

    @PostMapping()
    public TaskDto create(@RequestBody TaskRequest taskRequest){
        return taskService.createNew(taskRequest);
    }

    @PutMapping("/{id}")
    public TaskDto edit(@RequestBody TaskRequest taskRequest,
                               @PathVariable Long id){
        return taskService.update(taskRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        taskService.deleteById(id);
    }

}
