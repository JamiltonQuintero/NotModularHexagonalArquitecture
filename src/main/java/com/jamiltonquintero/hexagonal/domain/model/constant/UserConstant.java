package com.jamiltonquintero.hexagonal.domain.model.constant;

public class UserConstant {
    public static final String TASK_NOT_FOUND_MESSAGE_ERROR = "No se encontro una tarea con el id %s";
    public static final String CURRENT_USER_NOT_ALLOW_TO_DO_TASKS = "El total de horas (%s) en tareas supera las horas permitidas";
    public static final String CURRENT_USER_NOT_ALLOW_TO_DELETE = "El usuario %s no se puede eliminar porque tiene tareas pendientes por finalizar";
}
