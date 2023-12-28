package com.example.Repository;

import com.example.Model.TaskHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskHistoryRepo extends CrudRepository<TaskHistory,Integer> {
    @Query(value = "select username from user join task_history on user.id=task_history.previoususer_id where task_id=?",nativeQuery = true)
    List<String> getHistory(Integer taskId);
}
