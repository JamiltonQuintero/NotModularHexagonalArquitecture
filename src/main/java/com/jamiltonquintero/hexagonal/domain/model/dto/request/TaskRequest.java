package com.jamiltonquintero.hexagonal.domain.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskRequest {

    private String name;
    private String description;
    private int timeRequiredToComplete;

}
