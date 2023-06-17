package com.jamiltonquintero.hexagonal.domain.port;

import com.jamiltonquintero.hexagonal.domain.model.Task;

import java.util.List;

public interface TaskPersistencePort {

    Task create(Task request);
    Task getById(Long id);
    List<Task> getAll();
    void deleteById(Long id);
    Task update(Task request);
    List<Task> getByIds(List<Long> tasksIds);

}
