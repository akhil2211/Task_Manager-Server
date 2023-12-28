package com.example.Repository;

import com.example.Model.TaskCategory;
import org.springframework.data.repository.CrudRepository;

public interface TaskCategoryRepo extends CrudRepository<TaskCategory,Integer> {
}
