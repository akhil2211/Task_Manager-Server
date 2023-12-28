package com.example.Repository;

import com.example.Model.TaskPriority;
import org.springframework.data.repository.CrudRepository;

public interface TaskPriorityRepo extends CrudRepository<TaskPriority,Integer> {
}
