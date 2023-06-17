package com.jamiltonquintero.hexagonal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {

    private Long id;
    private String name;
    private String description;
    private boolean completed;
    private LocalDateTime dateOfCreation;
    private int timeRequiredToComplete;
    private List<User> users;

    public void setInitialValues(){
        this.completed = false;
        this.dateOfCreation = LocalDateTime.now();
    }

    public void setCompleted(){

        //

        this.completed = true;
    }

}
