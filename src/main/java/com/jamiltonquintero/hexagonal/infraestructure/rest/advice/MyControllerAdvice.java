package com.jamiltonquintero.hexagonal.infraestructure.rest.advice;


import com.jamiltonquintero.hexagonal.infraestructure.adapter.exception.TaskException;
import com.jamiltonquintero.hexagonal.infraestructure.adapter.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<String> handleEmptyInput(TaskException emptyInputException){
        return new ResponseEntity<String>(emptyInputException.getErrorMessage(), emptyInputException.getErrorCode());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleEmptyInput(UserException emptyInputException){
        return new ResponseEntity<String>(emptyInputException.getErrorMessage(), emptyInputException.getErrorCode());
    }

}
