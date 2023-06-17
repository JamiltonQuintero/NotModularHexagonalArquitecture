package com.jamiltonquintero.hexagonal.infraestructure.rest.controller;

import com.jamiltonquintero.hexagonal.application.usecases.UserService;
import com.jamiltonquintero.hexagonal.domain.model.dto.UserDto;
import com.jamiltonquintero.hexagonal.domain.model.dto.request.UserRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id){
        return userService.getById(id);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PostMapping()
    public UserDto create(@RequestBody UserRequest taskRequest){
        return userService.createNew(taskRequest);
    }

    @PutMapping("/{id}")
    public UserDto userEdit(@RequestBody UserRequest taskRequest,
                               @PathVariable Long id){
        return userService.update(taskRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteById(id);
    }

    @PostMapping("/{id}/tasks")
    public void assignTasks(@PathVariable Long id , @RequestParam List<Long> tasksIds){
        userService.assignTasks(id, tasksIds);
    }

}
