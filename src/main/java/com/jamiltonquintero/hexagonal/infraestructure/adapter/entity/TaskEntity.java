package com.jamiltonquintero.hexagonal.infraestructure.adapter.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private boolean completed;
    private LocalDateTime dateOfCreation;
    private int timeRequiredToComplete;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "taskEntities")
    @JsonIgnore
    private Set<UserEntity> userEntities = new HashSet<>();

    public TaskEntity(Long id) {
        this.id = id;
    }

    public void setInitialValues(){
        this.completed = false;
        this.dateOfCreation = LocalDateTime.now();
    }

}
