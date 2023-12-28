package com.example.Repository;

import com.example.Model.Assignment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AssignmentRepo extends CrudRepository<Assignment,Integer> {
    @Query(value="select* from assignment where task_id=?",nativeQuery = true)
    Optional<Assignment> findAssignmentByTask(Integer taskHolderId);
}
