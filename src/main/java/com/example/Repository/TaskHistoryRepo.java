package com.example.Repository;

import com.example.Model.TaskHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface TaskHistoryRepo extends CrudRepository<TaskHistory,Integer> {
    @Query(value = "select u.username, th.unassigned_at,th.assigned_at,u.firstname,u.lastname from user as u join task_history as th on u.id=th.previoususer_id where task_id=? order by th.unassigned_at DESC",nativeQuery = true)
    List<Map<String,Object>> getHistory(Integer taskId);
}
