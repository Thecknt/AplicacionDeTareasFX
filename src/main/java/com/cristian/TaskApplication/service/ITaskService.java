package com.cristian.TaskApplication.service;

import com.cristian.TaskApplication.model.Task;

import java.util.List;

public interface ITaskService {

    public List<Task> showAllTask();

    public Task searchById(Integer idTask);

    public void saveTask(Task task); //Para actualizar o agregar, va a depender del id,
    // si es null agrega, sino actualiza.

    public void deleteTask(Task task);
}
