package com.example.Repository;

import com.example.Model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends CrudRepository<Comment,Integer> {
    @Query(value = "select * from comment where task_id=?", nativeQuery = true)
    List<Comment> findTaskComment(Integer taskid);

}
