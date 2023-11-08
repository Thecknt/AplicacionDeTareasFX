package com.cristian.TaskApplication.repository;

import com.cristian.TaskApplication.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryTask extends JpaRepository<Task, Integer>{

}
